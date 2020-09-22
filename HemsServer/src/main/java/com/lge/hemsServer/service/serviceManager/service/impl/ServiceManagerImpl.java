package com.lge.hemsServer.service.serviceManager.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionAccessKeyNotFound;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceManagerInternalFailure;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameConflict;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameNotFound;
import com.lge.hemsServer.service.serviceManager.service.ServiceManager;
import com.lge.hemsServer.service.serviceManager.service.dao.ServiceSettingRepository;
import com.lge.hemsServer.service.serviceManager.service.dao.dto.ServiceSettingDto;

@Service("serviceManager")
public class ServiceManagerImpl implements ServiceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);
	
	@Autowired
	ServiceSettingRepository serviceSettingRepository;
	
	private String serviceName;
	
	private String accessKey;
	
	@PostConstruct
	public void init() {
		//
		try {
			// 1) 기동 시 DB 로 부터 이름을 읽고 기록
			ServiceSettingDto serviceSettingDto = getSettingDto();
			if (serviceSettingDto != null) {
				serviceName = serviceSettingDto.getServiceName();
				accessKey = serviceSettingDto.getAccessKey();
			}
		} catch (Exception e) {
			logger.error("[ServiceManager].init : error = " + e);
		}
	}
	
	@Override
	public Boolean setServiceName(String serviceName, String accessKey) throws ExceptionServiceNameConflict, ExceptionServiceManagerInternalFailure {
		//
		ServiceSettingDto serviceSettingDto = null;
		try {
			serviceSettingDto = getSettingDto();
		} catch (Exception e) {
			logger.error("[ServiceManager].setServiceName : error = " + e);
			throw new ExceptionServiceManagerInternalFailure();
		}
		if (serviceSettingDto != null)
			throw new ExceptionServiceNameConflict();
		
		try {
			if(setSettingDto(new ServiceSettingDto(serviceName, accessKey))) {
				this.serviceName = serviceName;
				this.accessKey = accessKey;
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[ServiceManager].setServiceName : error = " + e);
			throw new ExceptionServiceManagerInternalFailure();
		}
	}

	@Override
	public String getServiceName() throws ExceptionServiceNameNotFound {
		//
		if (serviceName == null || serviceName.isEmpty())
			throw new ExceptionServiceNameNotFound();
		
		return serviceName;
	}
	
	public String getAccessKey() throws ExceptionAccessKeyNotFound {
		//
		if (accessKey == null || accessKey.isEmpty())
			throw new ExceptionAccessKeyNotFound();
		
		return accessKey;
	}
	
	@Override
	public Boolean resetServiceName(String newServiceName, String newAccessKey) throws ExceptionServiceNameNotFound, ExceptionServiceManagerInternalFailure {
		//
		if (serviceName == null || serviceName.isEmpty()) {
			try {
				return setServiceName(newServiceName, newAccessKey);
			} catch (ExceptionServiceNameConflict e) {
				logger.error("[ServiceManager].resetServiceName : error = " + e);
				throw new ExceptionServiceManagerInternalFailure();
			}
		}
		else {
			Boolean result = false;
			try {
				result = deleteServiceName();
			} catch (ExceptionServiceNameNotFound | ExceptionServiceManagerInternalFailure e) {
				throw new ExceptionServiceManagerInternalFailure();
			}
			
			if (result) {
				try {
					return setServiceName(newServiceName, newAccessKey);
				} catch (ExceptionServiceNameConflict e) {
					logger.error("[ServiceManager].resetServiceName : error = " + e);
					throw new ExceptionServiceManagerInternalFailure();
				}
			}
			return false;
		}
	}

	@Override
	public Boolean deleteServiceName() throws ExceptionServiceNameNotFound, ExceptionServiceManagerInternalFailure {
		//
		if (serviceName == null || serviceName.isEmpty())
			throw new ExceptionServiceNameNotFound();
		
		try {
			deleteSettingDto(new ServiceSettingDto(serviceName, accessKey));
			return true;
		} catch (Exception e) {
			logger.error("[ServiceManager].deleteServiceName : error = " + e);
			throw new ExceptionServiceManagerInternalFailure();
		}
	}

	private Boolean setSettingDto(ServiceSettingDto serviceSettingDto) throws Exception {
		//
		try {
			ServiceSettingDto savedServiceSettingDto = serviceSettingRepository.save(serviceSettingDto);
			if (savedServiceSettingDto != null) {
				if (savedServiceSettingDto.equals(serviceSettingDto))
					return true;
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private ServiceSettingDto getSettingDto() throws Exception {
		//
		try {
			List<ServiceSettingDto> results = serviceSettingRepository.findAll();
			if (results.isEmpty())
				return null;
			else
				return results.get(0);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void deleteSettingDto(ServiceSettingDto serviceSettingDto) throws Exception {
		//
		try {
			serviceSettingRepository.delete(serviceSettingDto);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}