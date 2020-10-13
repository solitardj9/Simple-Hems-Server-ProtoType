package com.lge.hemsServer.application.idMapManager.model.exception;

public class ExceptionPrevIdNotFound extends Exception{
	
	private static final long serialVersionUID = -8969556191556511445L;

	private final int ERR_CODE;
	
	public ExceptionPrevIdNotFound() {
		//
    	super(ExceptionIdMapManagerCode.Not_Found.getMessage());
    	ERR_CODE = ExceptionIdMapManagerCode.Not_Found.getCode();
    }
    
	public ExceptionPrevIdNotFound(Throwable cause) {
		//
		super(ExceptionIdMapManagerCode.Not_Found.getMessage(), cause);
		ERR_CODE = ExceptionIdMapManagerCode.Not_Found.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}