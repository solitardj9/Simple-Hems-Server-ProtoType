package com.lge.hemsServer.service.serviceManager.model.exception;

public class ExceptionServiceNameConflict extends Exception{

	private static final long serialVersionUID = 1731299264792869085L;
	
	private final int ERR_CODE;
	
	public ExceptionServiceNameConflict() {
		//
    	super(ExceptionServiceManagerCode.CONFLICT.getMessage());
    	ERR_CODE = ExceptionServiceManagerCode.CONFLICT.getCode();
    }
    
	public ExceptionServiceNameConflict(Throwable cause) {
		//
		super(ExceptionServiceManagerCode.CONFLICT.getMessage(), cause);
		ERR_CODE = ExceptionServiceManagerCode.CONFLICT.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}