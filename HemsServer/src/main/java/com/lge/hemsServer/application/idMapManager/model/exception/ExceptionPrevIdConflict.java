package com.lge.hemsServer.application.idMapManager.model.exception;

public class ExceptionPrevIdConflict extends Exception{

	private static final long serialVersionUID = -6710415908879554241L;

	private final int ERR_CODE;
	
	public ExceptionPrevIdConflict() {
		//
    	super(ExceptionIdMapManagerCode.CONFLICT.getMessage());
    	ERR_CODE = ExceptionIdMapManagerCode.CONFLICT.getCode();
    }
    
	public ExceptionPrevIdConflict(Throwable cause) {
		//
		super(ExceptionIdMapManagerCode.CONFLICT.getMessage(), cause);
		ERR_CODE = ExceptionIdMapManagerCode.CONFLICT.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}