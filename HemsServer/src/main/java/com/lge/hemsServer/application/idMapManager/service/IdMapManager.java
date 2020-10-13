package com.lge.hemsServer.application.idMapManager.service;

import java.util.Map;

import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionIdMapManagerInternalFailure;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdConflict;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdNotFound;

public interface IdMapManager {
	//
	public Boolean addMappedId(String prevId, String nextId) throws ExceptionPrevIdConflict, ExceptionIdMapManagerInternalFailure;
	
	public Boolean updateMappedId(String prevId, String nextId) throws ExceptionPrevIdNotFound, ExceptionIdMapManagerInternalFailure;
	
	public String/*nextId*/ getMappedId(String prevId) throws ExceptionPrevIdNotFound;
	
	public Map<String/*prevId*/, String/*nextId*/> getMappedIds();
	
	public Boolean deleteMappedId(String prevId) throws ExceptionPrevIdNotFound, ExceptionIdMapManagerInternalFailure;
}