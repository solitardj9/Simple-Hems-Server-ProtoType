package com.lge.hemsServer.application.queueManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.application.queueManager.service.dao.dto.MonitoringQueueSettingDto;

public interface MonitoringQueueSettingRepository extends JpaRepository<MonitoringQueueSettingDto, String> {
	//
}