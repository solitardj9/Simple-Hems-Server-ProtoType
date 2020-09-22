package com.lge.hemsServer.application.queueManager.model.exception;

public class ExceptionQueueManagerInternalFailure extends Exception{

	private static final long serialVersionUID = 4035180201486299040L;
	
	private final int ERR_CODE;
	
	public ExceptionQueueManagerInternalFailure() {
		//
    	super(ExceptionQueueManagerCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionQueueManagerCode.Internal_Failure.getCode();
    }
    
	public ExceptionQueueManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionQueueManagerCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionQueueManagerCode.Internal_Failure.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}