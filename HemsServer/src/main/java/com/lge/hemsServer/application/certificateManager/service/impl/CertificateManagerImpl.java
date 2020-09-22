package com.lge.hemsServer.application.certificateManager.service.impl;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateManagerInternalFailure;
import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateNotFound;
import com.lge.hemsServer.application.certificateManager.service.CertificateManager;
import com.lge.hemsServer.application.certificateManager.service.dao.CertificateRepository;
import com.lge.hemsServer.application.certificateManager.service.dao.KeyPairRepository;
import com.lge.hemsServer.application.certificateManager.service.dao.dto.CertificateDto;
import com.lge.hemsServer.application.certificateManager.service.dao.dto.KeyPairDto;
import com.lge.hemsServer.utils.security.CertificateUtil;

@Service("certificateManager")
public class CertificateManagerImpl implements CertificateManager {
	
	private static final Logger logger = LoggerFactory.getLogger(CertificateManagerImpl.class);
	
	@Autowired
	KeyPairRepository keyPairRepository;
	
	@Autowired
	CertificateRepository certificateRepository;
	
	private KeyPair keyPair;
	
	private String certificate;
		        
    @Value("${hemsServer.application.certificateManager.keyPair.algorithm}")
    private String algorithm;
    
    @Value("${hemsServer.application.certificateManager.keyPair.signatureAlgorithm}")
    private String signatureAlgorithm;
	
	private static final Integer KEY_LENGTH = 2048;
	
	@PostConstruct
	public void init() {
		//
		KeyPairDto keyPairDto = getKeyPairDto();
		if (keyPairDto == null) {
			generateAndSaveKeyPair();
		}
		else {
			keyPair = makeKeyPair(keyPairDto);
		}
		
		try {
			CertificateDto certificateDto = getCertificateDto();
			if (certificateDto != null && certificateDto.getCertificate() != null && !certificateDto.getCertificate().isEmpty()) {
				this.certificate = certificateDto.getCertificate();
			}
		} catch (Exception e) {
			logger.error("[CertificateManager].init : " + e);
		}
	}
	
	@Override
	public Boolean makeKeyPair() {
		//
		return generateAndSaveKeyPair();
	}
	
	@Override
	public KeyPair getKeyPair() {
		//
		return keyPair;
	}
	
	@Override
	public KeyPair getKeyPairFromDB() {
		//
		KeyPairDto keyPairDto = getKeyPairDto();
		return makeKeyPair(keyPairDto);
	}
	
	@Override
	public Boolean deleteKeyPair() {
		//
		if (deleteKeyPairDto()) {
			keyPair = null;
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String generateCsr() {
		//
		String commonName = "CN=HemsServer";
		String organization = "O=Vpp";
		String department = "OU=Hems";
		String country = "C=NO";
		String city = "L=NO";
		
		String fixedStringInfo = country + ", " + city + ", " + department + ", " + organization + ", " + commonName;
		
		PKCS10CertificationRequest pkcs10CertificationRequest = null;
		try {
			pkcs10CertificationRequest = CertificateUtil.generatePKCS10CertificationRequest(signatureAlgorithm, keyPair, fixedStringInfo);
			return CertificateUtil.readCertificationRequestAsPem(pkcs10CertificationRequest);
		} catch (Exception e) {
			logger.error("[CertificateManager].generateCsr : " + e);
			return null;
		}
	}
	
	private Boolean generateAndSaveKeyPair() {
		//
		KeyPair keyPair = generateKeyPair();
		
		if (keyPair != null) {
			//
			if (setKeyPairDto(makeKeyPairDto(keyPair))) {
				this.keyPair = keyPair;
				return true;
			}
			return false;
		}
		return false;
	}
	
	private KeyPair generateKeyPair() {
		//
		try {
			return CertificateUtil.generateKeyPair(algorithm, KEY_LENGTH);
		} catch (Exception e) {
			logger.error("[CertificateManager].generateKeyPair : error = " + e);
			return null;
		}
	}
	
	private KeyPairDto makeKeyPairDto(KeyPair keyPair) {
		//
		try {
			String publicKey = CertificateUtil.publicKeyAsPem(keyPair.getPublic());
			String privateKey = CertificateUtil.privateKeyAsPem(keyPair.getPrivate());
			
			KeyPairDto keyPairDto = new KeyPairDto(publicKey, privateKey);
			return keyPairDto;
		} catch (CertificateEncodingException | IOException e) {
			logger.error("[CertificateManager].makeKeyPairDto : error = " + e);
			return null;
		}
	}
	
	private KeyPair makeKeyPair(KeyPairDto keyPairDto) {
		//
		if (keyPairDto.getPrivateKey() != null && keyPairDto.getPublicKey() != null) {
			PublicKey publicKey = CertificateUtil.readPublicKey(keyPairDto.getPublicKey(), algorithm);
			PrivateKey privateKey = CertificateUtil.readPrivateKey(keyPairDto.getPrivateKey(), algorithm);
			
			KeyPair keyPair = new KeyPair(publicKey, privateKey);
			return keyPair;
		}
		else
			return null;
		
	}
	
	private KeyPairDto getKeyPairDto() {
		//
		Optional<KeyPairDto> result = keyPairRepository.findById(1);
		if (result.isPresent()){
			KeyPairDto keyPairDto = result.get();
			return keyPairDto;
		}
		else{
			return null;
		}
	}
	
	private Boolean setKeyPairDto(KeyPairDto keyPairDto) {
		//
		if (keyPairDto == null)
			return false;
		
		try {
			KeyPairDto savedKeyPairDto = getKeyPairDto();
			if (savedKeyPairDto == null) {
				keyPairRepository.save(keyPairDto);
			}
			else {
				savedKeyPairDto.setPrivateKey(keyPairDto.getPrivateKey());
				savedKeyPairDto.setPublicKey(keyPairDto.getPublicKey());
				keyPairRepository.save(savedKeyPairDto);
			}
			return true;
		} catch (Exception e) {
			logger.error("[CertificateManager].setKeyPairDto : error = " + e);
			return false;
		}
	}
	
	private Boolean deleteKeyPairDto() {
		//
		KeyPairDto keyPairDto = new KeyPairDto(1, null, null);
		try {
			return setKeyPairDto(keyPairDto);
		} catch (Exception e) {
			logger.error("[CertificateManager].deleteKeyPairDto : error = " + e);
			return false;
		}
	}

	@Override
	public Boolean setCertificate(String certificate) throws ExceptionCertificateManagerInternalFailure {
		//
		CertificateDto certificateDto = new CertificateDto(certificate);
		try {
			if (setCertificateDto(certificateDto)) {
				this.certificate = certificate;
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[CertificateManager].setCertificate : error = " + e);
			throw new ExceptionCertificateManagerInternalFailure();
		}
	}

	@Override
	public String getCertificate() throws ExceptionCertificateNotFound {
		//
		if (certificate == null || certificate.isEmpty()) {
			throw new ExceptionCertificateNotFound();
		}
		
		return certificate;
	}
	
	@Override
	public String getCertificateFromDB() throws ExceptionCertificateNotFound, ExceptionCertificateManagerInternalFailure {
		//
		CertificateDto certificateDto = null;
		try {
			certificateDto = getCertificateDto();
		} catch (Exception e) {
			logger.error("[CertificateManager].getCertificateFromDB : error = " + e);
			throw new ExceptionCertificateManagerInternalFailure();
		}
		
		if (certificateDto != null && certificateDto.getCertificate() != null && !certificateDto.getCertificate().isEmpty()) {
			return certificateDto.getCertificate();
		}
		else {
			throw new ExceptionCertificateNotFound();
		}
	}

	@Override
	public Boolean deleteCertificate() throws ExceptionCertificateManagerInternalFailure {
		//
		try {
			if (deleteCertificateDto()) {
				certificate = null;
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			logger.error("[CertificateManager].deleteCertificate : error = " + e);
			throw new ExceptionCertificateManagerInternalFailure();
		}
	}
	
	private Boolean setCertificateDto(CertificateDto certificateDto) throws Exception {
		//
		if (certificateDto == null)
			return false;
		
		try {
			CertificateDto savedCertificateDto = getCertificateDto();
			if (savedCertificateDto == null) {
				certificateRepository.save(certificateDto);
			}
			else {
				savedCertificateDto.setCertificate(certificateDto.getCertificate());
				certificateRepository.save(savedCertificateDto);
			}
			return true;
		} catch (Exception e) {
			logger.error("[CertificateManager].setCertificateDto : error = " + e);
			return false;
		}
	}
	
	private CertificateDto getCertificateDto() throws Exception {
		//
		Optional<CertificateDto> result = certificateRepository.findById(1);
		if (result.isPresent()){
			CertificateDto certificateDto = result.get();
			return certificateDto;
		}
		else{
			return null;
		}
	}
	
	private Boolean deleteCertificateDto() throws Exception {
		//
		CertificateDto certificateDto = new CertificateDto(1, null);
		try {
			return setCertificateDto(certificateDto);
		} catch (Exception e) {
			logger.error("[CertificateManager].deleteCertificateDto : error = " + e);
			return false;
		}
	}
}