package com.lge.hemsServer.application.idMapManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ID_MAPPER_SETTING")
public class IdMapSettingDto {
	//
	@Id
	@Column(name="PREV_ID")
	private String prevId;
	
	@Column(name="NEXT_ID")
	private String nextId;
	
	public IdMapSettingDto() {
	}

	public IdMapSettingDto(String prevId, String nextId) {
		super();
		this.prevId = prevId;
		this.nextId = nextId;
	}

	public String getPrevId() {
		return prevId;
	}

	public void setPrevId(String prevId) {
		this.prevId = prevId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nextId == null) ? 0 : nextId.hashCode());
		result = prime * result + ((prevId == null) ? 0 : prevId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdMapSettingDto other = (IdMapSettingDto) obj;
		if (nextId == null) {
			if (other.nextId != null)
				return false;
		} else if (!nextId.equals(other.nextId))
			return false;
		if (prevId == null) {
			if (other.prevId != null)
				return false;
		} else if (!prevId.equals(other.prevId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdMapSettingDto [prevId=" + prevId + ", nextId=" + nextId + "]";
	}
}