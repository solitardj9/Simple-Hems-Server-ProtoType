package com.lge.hemsServer.service.serviceManager.service;

import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionAccessKeyNotFound;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceManagerInternalFailure;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameConflict;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameNotFound;

public interface ServiceManager {
	//
	public Boolean setServiceName(String serviceName, String accessKey) throws ExceptionServiceNameConflict, ExceptionServiceManagerInternalFailure;
	
	public String getServiceName() throws ExceptionServiceNameNotFound;
	
	public String getAccessKey() throws ExceptionAccessKeyNotFound;
	
	public Boolean resetServiceName(String newServiceName, String newAccessKey) throws ExceptionServiceNameNotFound, ExceptionServiceManagerInternalFailure;
	
	public Boolean deleteServiceName() throws ExceptionServiceNameNotFound, ExceptionServiceManagerInternalFailure;
}