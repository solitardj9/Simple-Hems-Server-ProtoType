package com.lge.hemsServer.application.certificateManager.model.exception;

public class ExceptionCertificateConflict extends Exception{

	private static final long serialVersionUID = -405641646884511124L;
	
	private final int ERR_CODE;
	
	public ExceptionCertificateConflict() {
		//
    	super(ExceptionCertificateManagerCode.CONFLICT.getMessage());
    	ERR_CODE = ExceptionCertificateManagerCode.CONFLICT.getCode();
    }
    
	public ExceptionCertificateConflict(Throwable cause) {
		//
		super(ExceptionCertificateManagerCode.CONFLICT.getMessage(), cause);
		ERR_CODE = ExceptionCertificateManagerCode.CONFLICT.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}