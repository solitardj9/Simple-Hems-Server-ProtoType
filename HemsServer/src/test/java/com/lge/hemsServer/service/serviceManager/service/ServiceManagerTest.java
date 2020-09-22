package com.lge.hemsServer.service.serviceManager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionAccessKeyNotFound;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceManagerInternalFailure;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameConflict;
import com.lge.hemsServer.service.serviceManager.model.exception.ExceptionServiceNameNotFound;
import com.lge.hemsServer.service.serviceManager.service.ServiceManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ServiceManagerTest {

	@Autowired
	ServiceManager serviceManager;
	
	@Test
	public void testAll() {
		//
		String serviceName = "testServiceName";
		String newServiceName = "testServiceName2";
		String accessKey = "asdasfdvs325vsd";
		
		try {
			System.out.println("------get service name------");
			System.out.println(serviceManager.getServiceName());
			System.out.println(serviceManager.getAccessKey());
		} catch (ExceptionServiceNameNotFound | ExceptionAccessKeyNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------set service name------");
			System.out.println(serviceManager.setServiceName(serviceName, accessKey));
		} catch (ExceptionServiceNameConflict | ExceptionServiceManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get service name again------");
			System.out.println(serviceManager.getServiceName());
			System.out.println(serviceManager.getAccessKey());
		} catch (ExceptionServiceNameNotFound | ExceptionAccessKeyNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete service name------");
			System.out.println(serviceManager.deleteServiceName());
		} catch (ExceptionServiceNameNotFound | ExceptionServiceManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------reset service name------");
			System.out.println(serviceManager.resetServiceName(newServiceName, accessKey));
		} catch (ExceptionServiceManagerInternalFailure | ExceptionServiceNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get service name again------");
			System.out.println(serviceManager.getServiceName());
			System.out.println(serviceManager.getAccessKey());
		} catch (ExceptionServiceNameNotFound | ExceptionAccessKeyNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete service name------");
			System.out.println(serviceManager.deleteServiceName());
		} catch (ExceptionServiceNameNotFound | ExceptionServiceManagerInternalFailure e) {
			e.printStackTrace();
			
		}
	}
}