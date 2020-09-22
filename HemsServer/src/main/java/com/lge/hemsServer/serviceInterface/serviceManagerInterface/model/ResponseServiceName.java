package com.lge.hemsServer.serviceInterface.serviceManagerInterface.model;

import com.lge.hemsServer.serviceInterface.common.model.ResponseDefualt;

public class ResponseServiceName extends ResponseDefualt {
	//
	private String serviceName;
	
	public ResponseServiceName() {
		
	}

	public ResponseServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public ResponseServiceName(Integer status, String serviceName) {
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