package com.lge.hemsServer.application.certificateManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="CERTIFICATE")
public class CertificateDto {
	//
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;					// ID
	
	@Column(name="certificate")
	@Lob
	private String certificate;		// certificate
	
	public CertificateDto() {
		
	}
	
	public CertificateDto(int id) {
		this.id = id;
	}
	
	public CertificateDto(String certificate) {
		this.certificate = certificate;
	}
	
	public CertificateDto(int id, String certificate) {
		this.id = id;
		this.certificate = certificate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "CertificateDto [id=" + id + ", certificate=" + certificate + "]";
	}
}