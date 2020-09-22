package com.lge.hemsServer.application.queueManager.model.exception;

public class ExceptionControlQueueNameNotFound extends Exception{
	
	private static final long serialVersionUID = 537490317757257345L;
	
	private final int ERR_CODE;
	
	public ExceptionControlQueueNameNotFound() {
		//
    	super(ExceptionQueueManagerCode.C_Q_Not_Found.getMessage());
    	ERR_CODE = ExceptionQueueManagerCode.C_Q_Not_Found.getCode();
    }
    
	public ExceptionControlQueueNameNotFound(Throwable cause) {
		//
		super(ExceptionQueueManagerCode.C_Q_Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionQueueManagerCode.C_Q_Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}