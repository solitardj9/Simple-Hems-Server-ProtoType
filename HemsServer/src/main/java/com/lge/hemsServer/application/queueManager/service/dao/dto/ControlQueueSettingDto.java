package com.lge.hemsServer.application.queueManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="CONTROL_QUEUE_SETTING")
public class ControlQueueSettingDto {
	//
	@Id
	@Column(name="QUEUE_NAME")
	private String controlQueueName;
	
	public ControlQueueSettingDto() {
	}

	public ControlQueueSettingDto(String controlQueueName) {
		this.controlQueueName = controlQueueName;
	}

	public String getControlQueueName() {
		return controlQueueName;
	}

	public void setControlQueueName(String controlQueueName) {
		this.controlQueueName = controlQueueName;
	}

	@Override
	public String toString() {
		return "ControlQueueSettingDto [controlQueueName=" + controlQueueName + "]";
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
		ControlQueueSettingDto other = (ControlQueueSettingDto) obj;
		if (controlQueueName == null) {
			if (other.controlQueueName != null)
				return false;
		} else if (!controlQueueName.equals(other.controlQueueName))
			return false;
		return true;
	}
}