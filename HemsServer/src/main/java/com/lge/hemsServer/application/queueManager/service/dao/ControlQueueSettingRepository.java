package com.lge.hemsServer.application.queueManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.application.queueManager.service.dao.dto.ControlQueueSettingDto;

public interface ControlQueueSettingRepository extends JpaRepository<ControlQueueSettingDto, String> {
	//
}