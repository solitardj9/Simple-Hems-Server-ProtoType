package com.lge.hemsServer.application.certificateManager.service;

import java.security.KeyPair;

import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateConflict;
import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateManagerInternalFailure;
import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateNotFound;

public interface CertificateManager {
	//
	public Boolean makeKeyPair();
	
	public KeyPair getKeyPair();
	
	public KeyPair getKeyPairFromDB();
	
	public Boolean deleteKeyPair();
	
	public String generateCsr();
	
	public Boolean setCertificate(String certificate) throws ExceptionCertificateConflict, ExceptionCertificateManagerInternalFailure;
	
	public String getCertificate() throws ExceptionCertificateNotFound;
	
	public String getCertificateFromDB() throws ExceptionCertificateNotFound, ExceptionCertificateManagerInternalFailure;
	
	public Boolean deleteCertificate() throws ExceptionCertificateManagerInternalFailure;
}