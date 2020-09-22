package com.lge.hemsServer.service.serviceManager.model.exception;

public class ExceptionAccessKeyNotFound extends Exception{

	private static final long serialVersionUID = 6256827775220405956L;
	
	private final int ERR_CODE;
	
	public ExceptionAccessKeyNotFound() {
		//
    	super(ExceptionServiceManagerCode.Not_Found.getMessage());
    	ERR_CODE = ExceptionServiceManagerCode.Not_Found.getCode();
    }
    
	public ExceptionAccessKeyNotFound(Throwable cause) {
		//
		super(ExceptionServiceManagerCode.Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionServiceManagerCode.Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}