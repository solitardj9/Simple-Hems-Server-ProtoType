package com.lge.hemsServer.serviceInterface.queueManagerInterface.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionQueueManagerInternalFailure;
import com.lge.hemsServer.application.queueManager.service.QueueManager;
import com.lge.hemsServer.serviceInterface.common.model.ResponseDefualt;
import com.lge.hemsServer.serviceInterface.queueManagerInterface.model.ResponseMonitoringQueueName;

@RestController
@RequestMapping(value="/management")
public class MonitoringQueueController {
	
	private static final Logger logger = LoggerFactory.getLogger(MonitoringQueueController.class);

	@Autowired
	QueueManager queueManager;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/queue/{monitoringQueueName}")
	public ResponseEntity setMonitoringQueueName(@PathVariable("monitoringQueueName") String monitoringQueueName,
											  @RequestBody(required=false) String requestBody) {
		//
		logger.info("[MonitoringQueueController].setMonitoringQueueName is called.");
		
		try {
			if (queueManager.setMonitoringQueueName(monitoringQueueName)) {
				return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.OK.value(), monitoringQueueName), HttpStatus.OK);
			}
			else {
				logger.error("[MonitoringQueueController].setMonitoringQueueName is fail.");
				return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.INTERNAL_SERVER_ERROR.value(), monitoringQueueName), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionMonitoringQueueNameConflict e) {
			logger.error("[MonitoringQueueController].setMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.CONFLICT.value(), monitoringQueueName), HttpStatus.CONFLICT);
		} catch (ExceptionQueueManagerInternalFailure e) {
			logger.error("[MonitoringQueueController].setMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.INTERNAL_SERVER_ERROR.value(), monitoringQueueName), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PatchMapping(value="/queue/{monitoringQueueName}")
	public ResponseEntity resetMonitoringQueueName(@PathVariable("monitoringQueueName") String monitoringQueueName,
												@RequestBody(required=false) String requestBody) {
		//
		logger.info("[MonitoringQueueController].resetMonitoringQueueName is called.");
		
		try {
			if (queueManager.resetMonitoringQueueName(monitoringQueueName)) {
				return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.OK.value(), monitoringQueueName), HttpStatus.OK);
			}
			else {
				logger.error("[MonitoringQueueController].resetMonitoringQueueName is fail.");
				return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.INTERNAL_SERVER_ERROR.value(), monitoringQueueName), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionMonitoringQueueNameNotFound e) {
			logger.error("[MonitoringQueueController].resetMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.NOT_FOUND.value(), monitoringQueueName), HttpStatus.NOT_FOUND);
		} catch (ExceptionQueueManagerInternalFailure e) {
			logger.error("[MonitoringQueueController].resetMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseMonitoringQueueName(HttpStatus.INTERNAL_SERVER_ERROR.value(), monitoringQueueName), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value="/queue")
	public ResponseEntity deleteMonitoringQueueName(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[MonitoringQueueController].deleteMonitoringQueueName is called.");
		
		try {
			if (queueManager.deleteMonitoringQueueName()) {
				return new ResponseEntity<>(new ResponseDefualt(HttpStatus.OK.value()), HttpStatus.OK);
			}
			else {
				logger.error("[MonitoringQueueController].deleteMonitoringQueueName is fail.");
				return new ResponseEntity<>(new ResponseDefualt(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionMonitoringQueueNameNotFound e) {
			logger.error("[MonitoringQueueController].deleteMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
		} catch (ExceptionQueueManagerInternalFailure e) {
			logger.error("[MonitoringQueueController].deleteMonitoringQueueName : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}