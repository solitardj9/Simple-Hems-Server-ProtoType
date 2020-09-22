package com.lge.hemsServer.service.serviceManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="SERVICE_SETTING")
public class ServiceSettingDto {
	//
	@Id
	@Column(name="SERVICE_NAME")
	private String serviceName;
	
	@Column(name="ACCESS_KEY")
	private String accessKey;
	
	public ServiceSettingDto() {
		
	}

	public ServiceSettingDto(String serviceName, String accessKey) {
		super();
		this.serviceName = serviceName;
		this.accessKey = accessKey;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	@Override
	public String toString() {
		return "ServiceSettingDto [serviceName=" + serviceName + ", accessKey=" + accessKey + "]";
	}

	@Override
	public boolean equals(Object obj) {
		//
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceSettingDto other = (ServiceSettingDto) obj;
		if (accessKey == null) {
			if (other.accessKey != null)
				return false;
		} else if (!accessKey.equals(other.accessKey))
			return false;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		return true;
	}
}