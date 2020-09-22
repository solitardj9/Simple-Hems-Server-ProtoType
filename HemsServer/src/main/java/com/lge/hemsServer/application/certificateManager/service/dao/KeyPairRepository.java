package com.lge.hemsServer.application.certificateManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.application.certificateManager.service.dao.dto.KeyPairDto;

public interface KeyPairRepository extends JpaRepository<KeyPairDto, Integer> {
	//
}