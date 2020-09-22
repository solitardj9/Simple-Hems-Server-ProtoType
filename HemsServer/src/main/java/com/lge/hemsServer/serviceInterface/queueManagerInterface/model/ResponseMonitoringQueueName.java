package com.lge.hemsServer.serviceInterface.queueManagerInterface.model;

import com.lge.hemsServer.serviceInterface.common.model.ResponseDefualt;

public class ResponseMonitoringQueueName extends ResponseDefualt {
	//
	private String serviceName;
	
	public ResponseMonitoringQueueName() {
		
	}

	public ResponseMonitoringQueueName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public ResponseMonitoringQueueName(Integer status, String serviceName) {
		this.serviceName = serviceName;
		setStatus(status);
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "ResponseServiceName [serviceName=" + serviceName + ", toString()=" + super.toString() + "]";
	}
}