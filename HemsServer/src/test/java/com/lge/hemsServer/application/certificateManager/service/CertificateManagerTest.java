package com.lge.hemsServer.application.certificateManager.service;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateConflict;
import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateManagerInternalFailure;
import com.lge.hemsServer.application.certificateManager.model.exception.ExceptionCertificateNotFound;
import com.lge.hemsServer.utils.security.CertificateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CertificateManagerTest {

	@Autowired
	CertificateManager certificateManager;
	
	@Test
	public void testKeyPairAll() {
		//
		KeyPair keyPair = null;
		
		PublicKey publicKey1 = null;
		PublicKey publicKey2 = null;
				
		System.out.println("-----init key pair by delete-----");
		System.out.println(certificateManager.deleteKeyPair());
		
		System.out.println("-----make key pair-----");
		System.out.println(certificateManager.makeKeyPair());
		
		System.out.println("-----get key pair-----");
		try {
			keyPair = certificateManager.getKeyPair();
			System.out.println(CertificateUtil.publicKeyAsPem(keyPair.getPublic()));
			System.out.println(CertificateUtil.privateKeyAsPem(keyPair.getPrivate()));
		} catch (CertificateEncodingException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----get key pair from DB-----");
		keyPair = certificateManager.getKeyPairFromDB();
		try {
			System.out.println(CertificateUtil.publicKeyAsPem(keyPair.getPublic()));
			System.out.println(CertificateUtil.privateKeyAsPem(keyPair.getPrivate()));
		} catch (CertificateEncodingException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----make new key pair-----");
		System.out.println(certificateManager.makeKeyPair());
		
		System.out.println("-----get new key pair-----");
		try {
			keyPair = certificateManager.getKeyPair();
			System.out.println(CertificateUtil.publicKeyAsPem(keyPair.getPublic()));
			System.out.println(CertificateUtil.privateKeyAsPem(keyPair.getPrivate()));
			
			publicKey1 = keyPair.getPublic();
			
		} catch (CertificateEncodingException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----get new key pair from DB-----");
		try {
			keyPair = certificateManager.getKeyPairFromDB();
			System.out.println(CertificateUtil.publicKeyAsPem(keyPair.getPublic()));
			System.out.println(CertificateUtil.privateKeyAsPem(keyPair.getPrivate()));
			
			publicKey2 = keyPair.getPublic();
		} catch (CertificateEncodingException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("compare = " + publicKey1.equals(publicKey2));
		
		System.out.println("-----init key pair by delete-----");
		System.out.println(certificateManager.deleteKeyPair());
		
		System.out.println("-----get new key pair-----");
		System.out.println(certificateManager.getKeyPair());
		
		System.out.println("-----get new key pair from DB-----");
		System.out.println(certificateManager.getKeyPairFromDB());
	}
	
	@Test
	public void testCertificateAll() {
		//
		String certificate = "-----BEGIN CERTIFICATE-----\n" + 
				"MIIEowIBAAKCAQEAoOTa57nQQug0hDR9tbrxKRqQ3bzzDUI7/CMxv91i/Kv5rTDR\n" + 
				"A7l8l1HJmt4ImG82BFesHrMejPcnLZcveq3PNTzV4wiJ1RcVwXXtQNFVW6ioZtw8\n" + 
				"FakSUJp+Ny3jBr0ScUp+rYgnfFa3/x7OLtX1uytdGiN4+76eT8x2aV70qljPqy+J\n" + 
				"izh18EGYQno5FmfJplcFkxKvqcc5Eiz0ax+BfICR+zj/NsE3sJXviH69NnPlGu8B\n" + 
				"7b423yvHTh0r7ohbZ+GCZaD5vH0X7a6xp94NTR3Tb4iwGGobbWWqgxA8LPorMexv\n" + 
				"TVi1nrO1CDtskJ99Mw07u2zqvunKR9mwGZCh6wIDAQABAoIBAEv20Y4P8o7scIMs\n" + 
				"/19ytJMqGlS+9eiAKa8pdL84SRiwH9GQF1dRV8xgDrZCd7903oWdWUQjYNzAgmxp\n" + 
				"kb6z2n2xy/CF4wHLhPeS9KIiU9FTZ7Ms72dwrJeG4emujh5r3Y3E1NucybvQe84F\n" + 
				"3uZJGdy1pB8HuYLupHNbv1qPvSfBxv+GokGxTVehPWE4r0qHKKgfotYA8TVCWl80\n" + 
				"AZ6Yoz91nHm0cW5gygeLJvTQE9dnz2tLpqFzYKCwm/e6EN01YZOotNd9/95SWgCW\n" + 
				"7s7ptVEMaX9/kdIZyRFnDzxNm0sZ1ixu2TNt2td0yQERd+F1E13YmqOFOnKaSgRC\n" + 
				"z5kl7IkCgYEA4kXbaflTNYrfF0TXcJe1BgQK+k1qtqs3ziqZDVdL9y/AttNDJEa/\n" + 
				"4zMxor7NGRtDFukmONEXIyawMd0uUtoK26v8sErw0fmu6nglkaoYFg1Ch21bQgqB\n" + 
				"I5aSBmkXVIY8msxPA/f0mNMKUcXnJLUQDXmCAi2EsUj1AdWzrci9EjMCgYEAtggi\n" + 
				"xSSZYURQtjnxwcVg4oZI9VittV/GxAAgPvgbuABWXuH57LO3DEj2MXpF5SfybfUQ\n" + 
				"r6/YRjNGVbRDARzgn90I5quZI8yqW/sinTb72k+h00Vvgtkf8prMRbxvNbBFN30X\n" + 
				"LY2/w1lhR/l/pW0pB5Rpu/NU+5sjmepzDV1+KWkCgYEAmZh/+6ziXfsZuwrhD637\n" + 
				"lMzTwtsgcEVDiXCDGnpCt9WzhKdd8VcRVKHgtK++O6e03+1+I51Tjmnpur+AfX9D\n" + 
				"VaCm0+jbFj3eDY1nOzzyQq3XlmSWSPsmdEj2cOGPk7HXm+DKVlBPuhmv7Jhhchpy\n" + 
				"fiMYLHBHnax/nDI7WOFl7t8CgYAqybzKlHDNUU6blwFB6BB/DsuliEPEx4+kUNdD\n" + 
				"lfSGCuZChWHHBvN3GstsE+7MD9jHifxzH6V50uGskaPSZiRSz5UhC6MCrEA7QMwB\n" + 
				"IoSO63sBe2fb3QfKciKkHcgSWa3bfjbtiU1TMPZSAc7EZuVnF7bG7ErEndH/gwGq\n" + 
				"WZinSQKBgCl9cnV5YW8tt0ZS1MilRqw5yuaS6J+oiAmMH7mWj39LkoEbHBDX7YPF\n" + 
				"ltqWblBMF4suKqoJY+5sEpoJ8HLmpVUfToNeVqlIAbmpaUL1/XAQerUPSRGwEotq\n" + 
				"hB7688a6W6zC33sbibcJgaSeLWzD2sVWnPq68xChHhfT0zFfYqam\n" + 
				"-----END CERTIFICATE-----";
		
		try {
			System.out.println("-----set certificate-----");
			System.out.println(certificateManager.setCertificate(certificate));
		} catch (ExceptionCertificateConflict | ExceptionCertificateManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("-----get certificate-----");
			System.out.println(certificateManager.getCertificate());
		} catch (ExceptionCertificateNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("-----get certificate from db-----");
			System.out.println(certificateManager.getCertificateFromDB());
		} catch (ExceptionCertificateManagerInternalFailure | ExceptionCertificateNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("-----delete certificate-----");
			System.out.println(certificateManager.deleteCertificate());
		} catch (ExceptionCertificateManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("-----get certificate-----");
			System.out.println(certificateManager.getCertificate());
		} catch (ExceptionCertificateNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("-----get certificate from db-----");
			System.out.println(certificateManager.getCertificateFromDB());
		} catch (ExceptionCertificateManagerInternalFailure | ExceptionCertificateNotFound e) {
			e.printStackTrace();
		}
	}
}