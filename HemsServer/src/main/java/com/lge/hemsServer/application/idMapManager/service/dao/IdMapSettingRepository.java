package com.lge.hemsServer.application.idMapManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.application.idMapManager.service.dao.dto.IdMapSettingDto;

public interface IdMapSettingRepository extends JpaRepository<IdMapSettingDto, String> {
	//
}