package com.lge.hemsServer.utils.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class CertificateUtil {
	//
	private static final Logger logger = LoggerFactory.getLogger(CertificateUtil.class);
	
	private static Provider bc = null;
	
	private static void init() {
		if (bc == null) {
			bc = new BouncyCastleProvider();
			Security.insertProviderAt(bc, 1);
		}
	}
	
	public static X509Certificate generateX509Certificate(CertificateInfo certificateInfo, KeyPair keyPair, Date createdDate, Date expiredDate, String signatureAlgorithm) {
    	//
		init();
		
    	// 1) create Certificate Builder
    	X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(certificateInfo.getIssuerName(), 								// issuer authority
    																				 certificateInfo.getSerialNumber(),							// serial number of certificate
    																				 createdDate, 													// start of validity
    																				 expiredDate,														// end of validity
    																				 certificateInfo.getSubjectName(),								// subject name of certificate
    																				 keyPair.getPublic());											// public key of certificate  
    	
    	// 2) restrict Key Usage
    	try {
    		builder.addExtension(Extension.keyUsage, true, new KeyUsage(certificateInfo.getKeyUsage()));
    		builder.addExtension(Extension.basicConstraints, true, new BasicConstraints(certificateInfo.getBasicConstratints()));
    	} catch (CertIOException e) {
    		logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
    		return null;
        }
    	
    	// 3) create Certificate
    	ContentSigner signGen = null;
    	try {
    		signGen = new JcaContentSignerBuilder(signatureAlgorithm).setProvider("BC").build(keyPair.getPrivate());
    		return new JcaX509CertificateConverter().getCertificate(builder.build(signGen));
    	} catch (CertificateException | OperatorCreationException e) {
    		logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
        	return null;
        }
    }
	
	public static X509Certificate generateX509Certificate(CertificateInfo certificateInfo, X509Certificate caCertificate, PrivateKey privateKey,  PKCS10CertificationRequest pkcs10CertificationRequest, Date createdDate, Date expiredDate, String signatureAlgorithm) {
		//
		init();
		
		// 1) create CSR
		JcaPKCS10CertificationRequest jcaPKCS10CertificationRequest = new JcaPKCS10CertificationRequest(pkcs10CertificationRequest);
		
		// 2) create Certificate Builder
		X509v3CertificateBuilder builder;
		try {
			builder = new JcaX509v3CertificateBuilder(caCertificate,													// CA Certificate
															certificateInfo.getSerialNumber(),								// serial number of certificate
															createdDate,														// start of validity
															expiredDate,														// end of validity
															jcaPKCS10CertificationRequest.getSubject(),					// subject name of CSR
															jcaPKCS10CertificationRequest.getPublicKey());				// public key of CSR
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
    		return null;
		}
		
		// 2) restrict Key Usage
    	try {
    		builder.addExtension(Extension.keyUsage, true, new KeyUsage(certificateInfo.getKeyUsage()));
    		builder.addExtension(Extension.basicConstraints, true, new BasicConstraints(certificateInfo.getBasicConstratints()));
    	} catch (CertIOException e) {
    		logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
    		return null;
        }
    	
    	// 3) create Certificate
    	try {
			return new JcaX509CertificateConverter().getCertificate(builder.build(new JcaContentSignerBuilder(signatureAlgorithm).setProvider("BC").build(privateKey)));
		} catch (CertificateException | OperatorCreationException e) {
			logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
        	return null;
		}
	}
	
	public static X509Certificate readX509Certificate(String pem) {
		//
		init();
		
		if (pem == null || pem.isEmpty()) {
			logger.error("[CertificateUtil].generateX509Certificate : error = pem is null.");
        	return null;
		}

		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
			PemReader pemReader = new PemReader(new BufferedReader(new StringReader(pem)));
			PemObject pemObject = pemReader.readPemObject();
			
			if (pemObject != null) {
				X509Certificate cert = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(pemObject.getContent()));
				pemReader.close();
				return cert;
			}
			else {
				logger.error("[CertificateUtil].generateX509Certificate : error = pem object is null.");
				pemReader.close();
				return null;
			}
		} catch(Exception e) {
			logger.error("[CertificateUtil].generateX509Certificate : error = " + e);
			return null;
		}
	}
	
	public static PrivateKey readPrivateKey(String pem, String algorithm) {
		//
		PrivateKey privatekey = null;
		byte[] keyBytes = pem.getBytes(Charset.forName("UTF-8"));
		String strPrivateKey = null;

		try {
			strPrivateKey = new String(keyBytes, "UTF-8");
			strPrivateKey = strPrivateKey.replaceAll("(-+BEGIN RSA PRIVATE KEY-+\\r?\\n|-+END RSA PRIVATE KEY-+\\r?\\n?)", "");
			strPrivateKey = strPrivateKey.replaceAll("(-+BEGIN PRIVATE KEY-+\\r?\\n|-+END PRIVATE KEY-+\\r?\\n?)", "");

			BASE64Decoder decoder = new BASE64Decoder();
			keyBytes = decoder.decodeBuffer(strPrivateKey);

			// generate private key
			try {
				PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    			privatekey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			} catch(Exception e) {
				//e.printStackTrace();
				
				String exponentBase64 = "65537";
				RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(new BigInteger(1, keyBytes), new BigInteger(1, decoder.decodeBuffer(exponentBase64)));
    			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    			privatekey = keyFactory.generatePrivate(rsaPrivateKeySpec);
			}
		}catch (Exception e) {
			logger.error("[CertificateUtil].readPrivateKey : error = " + e);
			return null;
		}
		
		return privatekey; 
	}
	
	public static PublicKey readPublicKey(String pem, String algorithm) {
        //
		PublicKey publicKey = null;
		byte[] keyBytes = pem.getBytes(Charset.forName("UTF-8"));
		String strPublicKey = null;
		
		try {
			strPublicKey = new String(keyBytes, "UTF-8");
			strPublicKey = strPublicKey.replaceAll("(-+BEGIN RSA PUBLIC KEY-+\\r?\\n|-+END RSA PUBLIC KEY-+\\r?\\n?)", "");
			strPublicKey = strPublicKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
			
			BASE64Decoder decoder = new BASE64Decoder();
			keyBytes = decoder.decodeBuffer(strPublicKey);
			
			// generate public key
			try {
				PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
				publicKey = keyFactory.generatePublic(pkcs8EncodedKeySpec);
			} catch(Exception e) {
				//e.printStackTrace();
				
				X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
				publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			}
		} catch (Exception e) {
			logger.error("[CertificateUtil].readPublicKey : error = " + e);
			return null;
		}
		
		return publicKey; 
    }
	
	public static PKCS10CertificationRequest generatePKCS10CertificationRequest(String signatureAlgorithm, KeyPair subjectkey, String stringInfo) throws OperatorCreationException, NoSuchAlgorithmException {
        	//
        	PKCS10CertificationRequest csr = null;
        	
        	X500Principal subject = new X500Principal(stringInfo);
        	ContentSigner signGen = new JcaContentSignerBuilder(signatureAlgorithm).build(subjectkey.getPrivate());
		        
        	PKCS10CertificationRequestBuilder builder = new JcaPKCS10CertificationRequestBuilder(subject, subjectkey.getPublic());
        	
        	csr = builder.build(signGen);
        	
        	return csr;
	}
	
	public static String readCertificationRequestAsPem(PKCS10CertificationRequest pkcs10CertificationRequest) throws CertificateEncodingException, IOException {
		//
		StringWriter sw = new StringWriter();
		PemWriter writer = new PemWriter(sw);

		PemObject pemObject = new PemObject("CERTIFICATE REQUEST", pkcs10CertificationRequest.getEncoded());

		try {
		    writer.writeObject(pemObject);
		    writer.flush();
		}
		catch (IOException e) {
		    throw new RuntimeException(e);
		}
		finally {
		    writer.close();
		}
		return sw.toString();
	}
	
	public static PKCS10CertificationRequest readPKCS10CertificationRequest(String csr) {
		//
		try {
			ByteArrayInputStream pemStream = null;
			
			try {
				pemStream = new ByteArrayInputStream(csr.getBytes(Charset.forName("UTF-8")));
			} catch (Exception ex) {
				logger.error("[CertificateUtil].readPrivateKey : error = csr is not pem format.");
			}
			PEMParser pemParser = new PEMParser(new BufferedReader(new InputStreamReader(pemStream, StandardCharsets.UTF_8)));       
			Object object = pemParser.readObject();
			
			if (object instanceof PKCS10CertificationRequest) {
				PKCS10CertificationRequest pkcs10CertificationRequest = (PKCS10CertificationRequest)object;
				pemParser.close();
				return pkcs10CertificationRequest;
			}
			else {
				logger.error("[CertificateUtil].readPrivateKey : error = PKCS10CertificationRequest format error.");
				pemParser.close();
				return null;
			}
		} catch(Exception e) {
			logger.error("[CertificateUtil].readPKCS10CertificationRequest : error = " + e);
			return null;
		}
	}
	
	public static String makeX509CertificateAsPem(X509Certificate x509Certificate) throws CertificateEncodingException, IOException {
		//
		StringWriter sw = new StringWriter();
		PemWriter writer = new PemWriter(sw);
		
		PemObject pemObject = new PemObject("CERTIFICATE", x509Certificate.getEncoded());
		try {
			writer.writeObject(pemObject);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			writer.close();
		}
		
		return sw.toString();
	}
	
	public static String publicKeyAsPem(PublicKey key) throws CertificateEncodingException, IOException {
		//
		StringWriter sw = new StringWriter();
		JcaPEMWriter writer = new JcaPEMWriter(sw);
		
		try {
			writer.writeObject(key);
		} catch (IOException e) {
				throw new RuntimeException(e);
		} finally {
			writer.close();
		}

		return sw.getBuffer().toString();
	}
	
	public static String privateKeyAsPem(PrivateKey key) throws CertificateEncodingException, IOException {
		//
		StringWriter sw = new StringWriter();
		JcaPEMWriter writer = new JcaPEMWriter(sw);
		
		try {
			writer.writeObject(key);
		} catch (IOException e) {
				throw new RuntimeException(e);
		} finally {
			writer.close();
		}

		return sw.getBuffer().toString();
	}
	
	public static KeyPair generateKeyPair(String type, Integer size) throws Exception {
		//
		init();
		
		KeyPairGenerator kpGen = KeyPairGenerator.getInstance(type, "BC");
		kpGen.initialize(size, new SecureRandom());
		return kpGen.generateKeyPair();
	}
}