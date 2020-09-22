package com.lge.hemsServer.application.queueManager.model.exception;

public class ExceptionMonitoringQueueNameNotFound extends Exception{
	
	private static final long serialVersionUID = 537490317757257345L;
	
	private final int ERR_CODE;
	
	public ExceptionMonitoringQueueNameNotFound() {
		//
    	super(ExceptionQueueManagerCode.M_Q_Not_Found.getMessage());
    	ERR_CODE = ExceptionQueueManagerCode.M_Q_Not_Found.getCode();
    }
    
	public ExceptionMonitoringQueueNameNotFound(Throwable cause) {
		//
		super(ExceptionQueueManagerCode.M_Q_Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionQueueManagerCode.M_Q_Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}