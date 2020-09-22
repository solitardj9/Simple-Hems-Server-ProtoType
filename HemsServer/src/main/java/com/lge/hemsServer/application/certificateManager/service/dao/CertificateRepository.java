package com.lge.hemsServer.application.certificateManager.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lge.hemsServer.application.certificateManager.service.dao.dto.CertificateDto;

public interface CertificateRepository extends JpaRepository<CertificateDto, Integer> {
	//
}