package com.lge.hemsServer.application.queueManager.model.exception;

public class ExceptionMonitoringQueueNameConflict extends Exception{

	private static final long serialVersionUID = -3982392038554633383L;
	
	private final int ERR_CODE;
	
	public ExceptionMonitoringQueueNameConflict() {
		//
    	super(ExceptionQueueManagerCode.M_Q_CONFLICT.getMessage());
    	ERR_CODE = ExceptionQueueManagerCode.M_Q_CONFLICT.getCode();
    }
    
	public ExceptionMonitoringQueueNameConflict(Throwable cause) {
		//
		super(ExceptionQueueManagerCode.M_Q_CONFLICT.getMessage(), cause);
		ERR_CODE = ExceptionQueueManagerCode.M_Q_CONFLICT.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}