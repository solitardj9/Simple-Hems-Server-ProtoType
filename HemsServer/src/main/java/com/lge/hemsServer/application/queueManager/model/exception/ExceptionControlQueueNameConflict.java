package com.lge.hemsServer.application.queueManager.model.exception;

public class ExceptionControlQueueNameConflict extends Exception{

	private static final long serialVersionUID = -3982392038554633383L;
	
	private final int ERR_CODE;
	
	public ExceptionControlQueueNameConflict() {
		//
    	super(ExceptionQueueManagerCode.C_Q_CONFLICT.getMessage());
    	ERR_CODE = ExceptionQueueManagerCode.C_Q_CONFLICT.getCode();
    }
    
	public ExceptionControlQueueNameConflict(Throwable cause) {
		//
		super(ExceptionQueueManagerCode.C_Q_CONFLICT.getMessage(), cause);
		ERR_CODE = ExceptionQueueManagerCode.C_Q_CONFLICT.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}