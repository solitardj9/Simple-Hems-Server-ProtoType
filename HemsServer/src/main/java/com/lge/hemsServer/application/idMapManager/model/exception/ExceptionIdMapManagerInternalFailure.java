package com.lge.hemsServer.application.idMapManager.model.exception;

public class ExceptionIdMapManagerInternalFailure extends Exception{

	private static final long serialVersionUID = 7508607966305503833L;

	private final int ERR_CODE;
	
	public ExceptionIdMapManagerInternalFailure() {
		//
    	super(ExceptionIdMapManagerCode.Internal_Failure.getMessage());
    	ERR_CODE = ExceptionIdMapManagerCode.Internal_Failure.getCode();
    }
    
	public ExceptionIdMapManagerInternalFailure(Throwable cause) {
		//
		super(ExceptionIdMapManagerCode.Internal_Failure.getMessage(), cause);
		ERR_CODE = ExceptionIdMapManagerCode.Internal_Failure.getCode();
	}
	
	public int getErrCode() {
		//
		return ERR_CODE;
    }
}