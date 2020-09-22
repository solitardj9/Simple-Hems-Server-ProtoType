package com.lge.hemsServer.service.serviceManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.service.serviceManager.service.dao.dto.ServiceSettingDto;

public interface ServiceSettingRepository extends JpaRepository<ServiceSettingDto, String> {
	//
}