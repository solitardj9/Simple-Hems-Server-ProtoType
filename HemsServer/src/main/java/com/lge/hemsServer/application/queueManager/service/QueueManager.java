package com.lge.hemsServer.application.queueManager.service;

import java.util.List;

import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionQueueManagerInternalFailure;

public interface QueueManager {
	//
	public Boolean setMonitoringQueueName(String monitoringQueueName) throws ExceptionMonitoringQueueNameConflict, ExceptionQueueManagerInternalFailure;
	
	public String getMonitoringQueueName() throws ExceptionMonitoringQueueNameNotFound;
	
	public Boolean resetMonitoringQueueName(String newMonitoringQueueName) throws ExceptionMonitoringQueueNameNotFound, ExceptionQueueManagerInternalFailure;
	
	public Boolean deleteMonitoringQueueName() throws ExceptionMonitoringQueueNameNotFound, ExceptionQueueManagerInternalFailure;
	
	public Boolean addControlQueue(String controlQueueName) throws ExceptionControlQueueNameConflict, ExceptionQueueManagerInternalFailure;
	
	public List<String> getControlQueues() throws ExceptionControlQueueNameNotFound;
	
	public List<String> getControlQueuesFromDB() throws ExceptionQueueManagerInternalFailure;
	
	public Boolean deleteControlQueueName(String controlQueueName) throws ExceptionControlQueueNameNotFound, ExceptionQueueManagerInternalFailure;
	
	public Boolean deleteControlQueueNames() throws ExceptionQueueManagerInternalFailure;
}