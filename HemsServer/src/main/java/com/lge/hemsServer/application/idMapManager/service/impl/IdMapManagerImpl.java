package com.lge.hemsServer.application.idMapManager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionIdMapManagerInternalFailure;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdConflict;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdNotFound;
import com.lge.hemsServer.application.idMapManager.service.IdMapManager;
import com.lge.hemsServer.application.idMapManager.service.dao.IdMapSettingRepository;
import com.lge.hemsServer.application.idMapManager.service.dao.dto.IdMapSettingDto;
import com.lge.hemsServer.application.queueManager.service.impl.QueueManagerImpl;

@Service("idMapManager")
public class IdMapManagerImpl implements IdMapManager {
	
private static final Logger logger = LoggerFactory.getLogger(QueueManagerImpl.class);
	
	@Autowired
	IdMapSettingRepository idMapSettingRepository;
	
	private Map<String/*prevId*/, String/*nextId*/> idMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		//
	}

	@Override
	public Boolean addMappedId(String prevId, String nextId) throws ExceptionPrevIdConflict, ExceptionIdMapManagerInternalFailure {
		//
		IdMapSettingDto idMapSettingDto = null;
		try {
			idMapSettingDto = getIdMapSettingDto(prevId);
		} catch (Exception e) {
			logger.error("[IdMapManager].addMappedId : error = " + e);
			throw new ExceptionIdMapManagerInternalFailure();
		}
		
		if (idMapSettingDto != null)
			throw new ExceptionPrevIdConflict();
		
		try {
			if(setIdMapSettingDto(new IdMapSettingDto(prevId, nextId))) {
				this.idMap.put(prevId, nextId);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[IdMapManager].addMappedId : error = " + e);
			throw new ExceptionIdMapManagerInternalFailure();
		}
	}
	
	@Override
	public Boolean updateMappedId(String prevId, String nextId) throws ExceptionPrevIdNotFound, ExceptionIdMapManagerInternalFailure {
		//
		IdMapSettingDto idMapSettingDto = null;
		try {
			idMapSettingDto = getIdMapSettingDto(prevId);
		} catch (Exception e) {
			logger.error("[IdMapManager].updateMappedId : error = " + e);
			throw new ExceptionIdMapManagerInternalFailure();
		}
		
		if (idMapSettingDto == null)
			throw new ExceptionPrevIdNotFound();
		
		try {
			if(setIdMapSettingDto(new IdMapSettingDto(prevId, nextId))) {
				this.idMap.put(prevId, nextId);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[IdMapManager].updateMappedId : error = " + e);
			throw new ExceptionIdMapManagerInternalFailure();
		}
	}

	@Override
	public String getMappedId(String prevId) throws ExceptionPrevIdNotFound {
		//
		if (this.idMap.containsKey(prevId)) {
			return this.idMap.get(prevId);
		}
		else {
			throw new ExceptionPrevIdNotFound();
		}
	}
	
	@Override
	public Map<String/*prevId*/, String/*nextId*/> getMappedIds() {
		//
		return new HashMap<String, String>(this.idMap);
	}

	@Override
	public Boolean deleteMappedId(String prevId) throws ExceptionPrevIdNotFound, ExceptionIdMapManagerInternalFailure {
		//
		if (!this.idMap.containsKey(prevId)) {
			throw new ExceptionPrevIdNotFound();
		}
		
		try {
			deleteIdMapSettingDto(prevId);
			
			this.idMap.remove(prevId);
			
			return true;
		} catch (Exception e) {
			logger.error("[IdMapManager].deleteMappedId : error = " + e);
			throw new ExceptionIdMapManagerInternalFailure();
		}
	}

	private Boolean setIdMapSettingDto(IdMapSettingDto idMapSettingDto) throws Exception {
		//
		try {
			IdMapSettingDto savedIdMapSettingDto = idMapSettingRepository.save(idMapSettingDto);
			if (savedIdMapSettingDto != null) {
				if (savedIdMapSettingDto.equals(idMapSettingDto))
					return true;
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private IdMapSettingDto getIdMapSettingDto(String prevId) throws Exception {
		//
		try {
			Optional<IdMapSettingDto> result = idMapSettingRepository.findById(prevId);
			if (result.isPresent())
				return result.get();
			else
				return null;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void deleteIdMapSettingDto(String prevId) throws Exception {
		//
		try {
			idMapSettingRepository.deleteById(prevId);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}