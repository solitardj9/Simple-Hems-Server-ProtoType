package com.lge.hemsServer.application.queueManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="MONITORING_QUEUE_SETTING")
public class MonitoringQueueSettingDto {
	//
	@Id
	@Column(name="QUEUE_NAME")
	private String monitoringQueueName;
	
	public MonitoringQueueSettingDto() {
	}

	public MonitoringQueueSettingDto(String monitoringQueueName) {
		this.monitoringQueueName = monitoringQueueName;
	}

	public String getMonitoringQueueName() {
		return monitoringQueueName;
	}

	public void setMonitoringQueueName(String monitoringQueueName) {
		this.monitoringQueueName = monitoringQueueName;
	}

	@Override
	public String toString() {
		return "MonitoringQueueSettingDto [monitoringQueueName=" + monitoringQueueName + "]";
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
		MonitoringQueueSettingDto other = (MonitoringQueueSettingDto) obj;
		if (monitoringQueueName == null) {
			if (other.monitoringQueueName != null)
				return false;
		} else if (!monitoringQueueName.equals(other.monitoringQueueName))
			return false;
		return true;
	}
}