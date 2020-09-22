package com.lge.hemsServer.service.serviceManager.model.exception;

public class ExceptionServiceManagerInternalFailure extends Exception{
    
	private static final long serialVersionUID = 7594371506466712673L;
	
	private final int ERR_CODE;
	
	public ExceptionServiceManagerInternalFailure() {
		//
    	super(ExceptionServiceManagerCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionServiceManagerCode.Internal_Failure.getCode();
    }
    
	public ExceptionServiceManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionServiceManagerCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionServiceManagerCode.Internal_Failure.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}