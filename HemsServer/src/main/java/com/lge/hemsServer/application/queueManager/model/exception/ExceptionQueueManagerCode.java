package com.lge.hemsServer.application.queueManager.model.exception;

import java.util.HashMap;
import java.util.Map;


public enum ExceptionQueueManagerCode {
    //
	M_Q_Not_Found(404, "MonitoringQueueNameFoundException."),
	C_Q_Not_Found(404, "ControlQueueNameFoundException."),
	M_Q_CONFLICT(409, "MonitoringQueueNameIsAlreadyExistException."),
	C_Q_CONFLICT(409, "ControlQueueNameIsAlreadyExistException."),
	Internal_Failure(500, "InternalFailureException.")
    ;
 
    private Integer code;
    private String message;
 
    ExceptionQueueManagerCode(Integer code, String msg) {
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