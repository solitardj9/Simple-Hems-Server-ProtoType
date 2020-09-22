package com.lge.hemsServer.application.certificateManager.model.exception;

import java.util.HashMap;
import java.util.Map;


public enum ExceptionCertificateManagerCode {
    //
	Not_Found(404, "CertificateFoundException."),
	CONFLICT(409, "CertificateIsAlreadyExistException."),
	Internal_Failure(500, "InternalFailureException.")
    ;
 
    private Integer code;
    private String message;
 
    ExceptionCertificateManagerCode(Integer code, String msg) {
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