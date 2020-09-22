package com.lge.hemsServer.application.queueManager.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionQueueManagerInternalFailure;
import com.lge.hemsServer.application.queueManager.service.QueueManager;
import com.lge.hemsServer.application.queueManager.service.dao.ControlQueueSettingRepository;
import com.lge.hemsServer.application.queueManager.service.dao.MonitoringQueueSettingRepository;
import com.lge.hemsServer.application.queueManager.service.dao.dto.ControlQueueSettingDto;
import com.lge.hemsServer.application.queueManager.service.dao.dto.MonitoringQueueSettingDto;

@Service("queueManager")
public class QueueManagerImpl implements QueueManager {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueManagerImpl.class);
	
	@Autowired
	MonitoringQueueSettingRepository monitoringQueueSettingRepository;
	
	@Autowired
	ControlQueueSettingRepository controlQueueSettingRepository;
	
	private String monitoringQueueName;
	
	private Set<String> controlQueueNames = new HashSet<>();
	
	@PostConstruct
	public void init() {
		//
		try {
			// 1) 기동 시 DB 로 부터 이름을 읽고 기록
			MonitoringQueueSettingDto monitoringQueueSettingDto = getMonitoringQueueSettingDto();
			if (monitoringQueueSettingDto != null)
				monitoringQueueName = monitoringQueueSettingDto.getMonitoringQueueName();
		} catch (Exception e) {
			logger.error("[QueueManager].init - get monitoring queue : error = " + e);
		}
		
		try {
			// 1) 기동 시 DB 로 부터 이름을 읽고 기록
			List<ControlQueueSettingDto> controlQueueSettingDtos = getControlQueueSettingDtos();
			for (ControlQueueSettingDto iter : controlQueueSettingDtos) {
				controlQueueNames.add(iter.getControlQueueName());
			}
		} catch (Exception e) {
			logger.error("[QueueManager].init - get control queues : error = " + e);
		}
	}
	
	@Override
	public Boolean setMonitoringQueueName(String monitoringQueueName) throws ExceptionMonitoringQueueNameConflict, ExceptionQueueManagerInternalFailure {
		//
		MonitoringQueueSettingDto monitoringQueueSettingDto = null;
		try {
			monitoringQueueSettingDto = getMonitoringQueueSettingDto();
		} catch (Exception e) {
			logger.error("[QueueManager].setMonitoringQueueName : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
		if (monitoringQueueSettingDto != null)
			throw new ExceptionMonitoringQueueNameConflict();
		
		try {
			if(setMonitoringQueueSettingDto(new MonitoringQueueSettingDto(monitoringQueueName))) {
				this.monitoringQueueName = monitoringQueueName;
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[QueueManager].setMonitoringQueueName : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
	}

	@Override
	public String getMonitoringQueueName() throws ExceptionMonitoringQueueNameNotFound {
		//
		if (monitoringQueueName == null || monitoringQueueName.isEmpty())
			throw new ExceptionMonitoringQueueNameNotFound();
		
		return monitoringQueueName;
	}

	@Override
	public Boolean resetMonitoringQueueName(String newMonitoringQueueName) throws ExceptionMonitoringQueueNameNotFound, ExceptionQueueManagerInternalFailure {
		//
		if (monitoringQueueName == null || monitoringQueueName.isEmpty()) {
			try {
				return setMonitoringQueueName(monitoringQueueName);
			} catch (ExceptionMonitoringQueueNameConflict e) {
				logger.error("[QueueManager].resetMonitoringQueueName : error = " + e);
				throw new ExceptionQueueManagerInternalFailure();
			}
		}
		else {
			Boolean result = false;
			try {
				result = deleteMonitoringQueueName();
			} catch (ExceptionMonitoringQueueNameNotFound | ExceptionQueueManagerInternalFailure e) {
				throw new ExceptionQueueManagerInternalFailure();
			}
			
			if (result) {
				try {
					return setMonitoringQueueName(newMonitoringQueueName);
				} catch (ExceptionMonitoringQueueNameConflict e) {
					logger.error("[QueueManager].resetMonitoringQueueName : error = " + e);
					throw new ExceptionQueueManagerInternalFailure();
				}
			}
			return false;
		}
	}
	
	@Override
	public Boolean deleteMonitoringQueueName() throws ExceptionMonitoringQueueNameNotFound, ExceptionQueueManagerInternalFailure {
		//
		if (monitoringQueueName == null || monitoringQueueName.isEmpty())
			throw new ExceptionMonitoringQueueNameNotFound();
		
		try {
			deleteMonitoringQueueSettingDto(new MonitoringQueueSettingDto(monitoringQueueName));
			return true;
		} catch (Exception e) {
			logger.error("[QueueManager].deleteMonitoringQueueName : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
	}
	
	
	private Boolean setMonitoringQueueSettingDto(MonitoringQueueSettingDto monitoringQueueSettingDto) throws Exception {
		//
		try {
			MonitoringQueueSettingDto savedMonitoringQueueSettingDto = monitoringQueueSettingRepository.save(monitoringQueueSettingDto);
			if (savedMonitoringQueueSettingDto != null) {
				if (savedMonitoringQueueSettingDto.equals(monitoringQueueSettingDto))
					return true;
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private MonitoringQueueSettingDto getMonitoringQueueSettingDto() throws Exception {
		//
		try {
			List<MonitoringQueueSettingDto> results = monitoringQueueSettingRepository.findAll();
			if (results.isEmpty())
				return null;
			else
				return results.get(0);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void deleteMonitoringQueueSettingDto(MonitoringQueueSettingDto monitoringQueueSettingDto) throws Exception {
		//
		try {
			monitoringQueueSettingRepository.delete(monitoringQueueSettingDto);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean addControlQueue(String controlQueueName) throws ExceptionControlQueueNameConflict, ExceptionQueueManagerInternalFailure {
		//
		ControlQueueSettingDto controlQueueSettingDto;
		try {
			controlQueueSettingDto = getControlQueueSettingDto(controlQueueName);
		} catch (Exception e) {
			logger.error("[QueueManager].addControlQueue : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
		
		if (controlQueueSettingDto != null) {
			throw new ExceptionControlQueueNameConflict();
		}
		else {
			try {
				if (setControlQueueSettingDto(new ControlQueueSettingDto(controlQueueName))) {
					controlQueueNames.add(controlQueueName);
					return true;
				}
				else {
					return false;
				}
			} catch (Exception e) {
				logger.error("[QueueManager].addControlQueue : error = " + e);
				throw new ExceptionQueueManagerInternalFailure();
			}
		}
	}
	
	@Override
	public List<String> getControlQueues() throws ExceptionControlQueueNameNotFound {
		// 
		if (controlQueueNames == null)
			throw new ExceptionControlQueueNameNotFound();
		
		return new ArrayList<String>(controlQueueNames);
	}
	
	@Override
	public List<String> getControlQueuesFromDB() throws ExceptionQueueManagerInternalFailure {
		//
		List<String> retList = new ArrayList<>();
		
		try {
			List<ControlQueueSettingDto> controlQueueSettingDtos = getControlQueueSettingDtos();
			for (ControlQueueSettingDto iter : controlQueueSettingDtos) {
				retList.add(iter.getControlQueueName());
			}
		} catch (Exception e) {
			logger.error("[QueueManager].getControlQueuesFromDB : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
		
		return retList;
	}
	
	@Override
	public Boolean deleteControlQueueName(String controlQueueName) throws ExceptionControlQueueNameNotFound, ExceptionQueueManagerInternalFailure {
		//
		if (!controlQueueNames.contains(controlQueueName))
			throw new ExceptionControlQueueNameNotFound();
		
		try {
			deleteControlQueueSettingDto(new ControlQueueSettingDto(controlQueueName));
			controlQueueNames.remove(controlQueueName);
			return true;
		} catch (Exception e) {
			logger.error("[QueueManager].deleteControlQueueName : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
	}
	
	public Boolean deleteControlQueueNames() throws ExceptionQueueManagerInternalFailure {
		//
		try {
			deleteControlQueueSettingDtos();
			return true;
		} catch (Exception e) {
			logger.error("[QueueManager].deleteControlQueueNames : error = " + e);
			throw new ExceptionQueueManagerInternalFailure();
		}
	}
	
	private Boolean setControlQueueSettingDto(ControlQueueSettingDto controlQueueSettingDto) throws Exception {
		//
		try {
			ControlQueueSettingDto savedControlQueueSettingDto = controlQueueSettingRepository.save(controlQueueSettingDto);
			if (savedControlQueueSettingDto != null) {
				if (savedControlQueueSettingDto.equals(controlQueueSettingDto))
					return true;
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private List<ControlQueueSettingDto> getControlQueueSettingDtos() throws Exception {
		//
		try {
			List<ControlQueueSettingDto> results = controlQueueSettingRepository.findAll();
			return results;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private ControlQueueSettingDto getControlQueueSettingDto(String controlQueueName) throws Exception {
		//
		try {
			Optional<ControlQueueSettingDto> result = controlQueueSettingRepository.findById(controlQueueName);
			try {
				return result.get();
			} catch (NoSuchElementException e) {
				return null;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void deleteControlQueueSettingDto(ControlQueueSettingDto controlQueueSettingDto) throws Exception {
		//
		try {
			controlQueueSettingRepository.delete(controlQueueSettingDto);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void deleteControlQueueSettingDtos() throws Exception {
		//
		try {
			monitoringQueueSettingRepository.deleteAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}