package com.lge.hemsServer.serviceInterface.serviceManagerInterface.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceManagerInternalFailure;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameConflict;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameNotFound;
import com.lge.hemsServer.service.serviceManager.service.ServiceManager;
import com.lge.hemsServer.serviceInterface.common.model.ResponseDefualt;
import com.lge.hemsServer.serviceInterface.serviceManagerInterface.model.ResponseServiceName;

@RestController
@RequestMapping(value="/management")
public class ServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@Autowired
	ServiceManager serviceManager;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/service/{serviceName}")
	public ResponseEntity setServiceName(@PathVariable("serviceName") String serviceName,
											  @RequestParam(value = "accessKey", required=true) String accessKey,
											  @RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceController].setServiceName is called.");
		
		try {
			if (serviceManager.setServiceName(serviceName, accessKey)) {
				return new ResponseEntity<>(new ResponseServiceName(HttpStatus.OK.value(), serviceName), HttpStatus.OK);
			}
			else {
				logger.error("[ServiceController].setServiceName is fail.");
				return new ResponseEntity<>(new ResponseServiceName(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceName), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionServiceNameConflict e) {
			logger.error("[ServiceController].setServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseServiceName(HttpStatus.CONFLICT.value(), serviceName), HttpStatus.CONFLICT);
		} catch (ExceptionServiceManagerInternalFailure e) {
			logger.error("[ServiceController].setServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseServiceName(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceName), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PatchMapping(value="/service/{serviceName}")
	public ResponseEntity resetServiceName(@PathVariable("serviceName") String serviceName,
												@RequestParam(value = "accessKey", required=true) String accessKey,
												@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceController].resetServiceName is called.");
		
		try {
			if (serviceManager.resetServiceName(serviceName, accessKey)) {
				return new ResponseEntity<>(new ResponseServiceName(HttpStatus.OK.value(), serviceName), HttpStatus.OK);
			}
			else {
				logger.error("[ServiceController].resetServiceName is fail.");
				return new ResponseEntity<>(new ResponseServiceName(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceName), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionServiceNameNotFound e) {
			logger.error("[ServiceController].resetServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseServiceName(HttpStatus.NOT_FOUND.value(), serviceName), HttpStatus.NOT_FOUND);
		} catch (ExceptionServiceManagerInternalFailure e) {
			logger.error("[ServiceController].resetServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseServiceName(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceName), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value="/service")
	public ResponseEntity deleteServiceName(@RequestBody(required=false) String requestBody) {
		//
		logger.info("[ServiceController].deleteServiceName is called.");
		
		try {
			if (serviceManager.deleteServiceName()) {
				return new ResponseEntity<>(new ResponseDefualt(HttpStatus.OK.value()), HttpStatus.OK);
			}
			else {
				logger.error("[ServiceController].deleteServiceName is fail.");
				return new ResponseEntity<>(new ResponseDefualt(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ExceptionServiceNameNotFound e) {
			logger.error("[ServiceController].deleteServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
		} catch (ExceptionServiceManagerInternalFailure e) {
			logger.error("[ServiceController].deleteServiceName : error = " + e);
			return new ResponseEntity<>(new ResponseDefualt(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}