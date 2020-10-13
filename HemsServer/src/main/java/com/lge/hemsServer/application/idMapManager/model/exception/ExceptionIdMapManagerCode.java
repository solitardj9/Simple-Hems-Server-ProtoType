package com.lge.hemsServer.application.idMapManager.model.exception;

import java.util.HashMap;
import java.util.Map;


public enum ExceptionIdMapManagerCode {
    //
	Not_Found(404, "PreviousIdNotFoundException."),
	CONFLICT(409, "PreviousIdIsAlreadyExistException."),
	Internal_Failure(500, "InternalFailureException.")
    ;
 
    private Integer code;
    private String message;
 
    ExceptionIdMapManagerCode(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }
    public Integer getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
    
    public Map<String,String> getMapMessage() {
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put("message", this.getMessage());
		return messageMap;
	}
}