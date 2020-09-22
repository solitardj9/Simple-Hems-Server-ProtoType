package com.lge.hemsServer.application.certificateManager.service.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="kEY_PAIR")
public class KeyPairDto {
	//
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;					// ID
	
	@Column(name="public_key")
	@Lob
	private String publicKey;		// public key
	
	@Column(name="private_key")
	@Lob
	private String privateKey;		// private key
	
	public KeyPairDto() {
		
	}
	
	public KeyPairDto(int id) {
		this.id = id;
	}
	
	public KeyPairDto(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public KeyPairDto(int id, String publicKey, String privateKey) {
		this.id = id;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String toString() {
		return "KeyPairDto [id=" + id + ", publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
	}
}