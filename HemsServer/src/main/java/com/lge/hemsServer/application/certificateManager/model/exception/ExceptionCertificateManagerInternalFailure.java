package com.lge.hemsServer.application.certificateManager.model.exception;

public class ExceptionCertificateManagerInternalFailure extends Exception{
    
	private static final long serialVersionUID = 5528533910682611160L;
	
	private final int ERR_CODE;
	
	public ExceptionCertificateManagerInternalFailure() {
		//
    	super(ExceptionCertificateManagerCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionCertificateManagerCode.Internal_Failure.getCode();
    }
    
	public ExceptionCertificateManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionCertificateManagerCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionCertificateManagerCode.Internal_Failure.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}