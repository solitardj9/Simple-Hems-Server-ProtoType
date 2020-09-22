package com.lge.hemsServer.application.certificateManager.model.exception;

public class ExceptionCertificateNotFound extends Exception{

	private static final long serialVersionUID = 7278350091074334852L;
	
	private final int ERR_CODE;
	
	public ExceptionCertificateNotFound() {
		//
    	super(ExceptionCertificateManagerCode.Not_Found.getMessage());
    	ERR_CODE = ExceptionCertificateManagerCode.Not_Found.getCode();
    }
    
	public ExceptionCertificateNotFound(Throwable cause) {
		//
		super(ExceptionCertificateManagerCode.Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionCertificateManagerCode.Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}