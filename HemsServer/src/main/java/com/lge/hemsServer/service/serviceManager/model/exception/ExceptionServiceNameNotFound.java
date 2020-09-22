package com.lge.hemsServer.service.serviceManager.model.exception;

public class ExceptionServiceNameNotFound extends Exception{

	private static final long serialVersionUID = -2356521633669702231L;
	
	private final int ERR_CODE;
	
	public ExceptionServiceNameNotFound() {
		//
    	super(ExceptionServiceManagerCode.Not_Found.getMessage());
    	ERR_CODE = ExceptionServiceManagerCode.Not_Found.getCode();
    }
    
	public ExceptionServiceNameNotFound(Throwable cause) {
		//
		super(ExceptionServiceManagerCode.Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionServiceManagerCode.Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}