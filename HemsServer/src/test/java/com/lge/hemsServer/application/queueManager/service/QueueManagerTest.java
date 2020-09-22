package com.lge.hemsServer.application.queueManager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionControlQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameConflict;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionMonitoringQueueNameNotFound;
import com.lge.hemsServer.application.queueManager.model.exception.ExceptionQueueManagerInternalFailure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class QueueManagerTest {

	@Autowired
	QueueManager queueManager;
	
	@Test
	public void testMQAll() {
		//
		String monitoringQueueName = "M-Q-Name-C1";
		String newMonitoringQueueName = "M-Q-Name-C2";
		
		try {
			System.out.println("------get monitoring queue name------");
			queueManager.getMonitoringQueueName();
		} catch (ExceptionMonitoringQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------set monitoring queue name------");
			System.out.println(queueManager.setMonitoringQueueName(monitoringQueueName));
		} catch (ExceptionMonitoringQueueNameConflict | ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get monitoring queue name again------");
			System.out.println(queueManager.getMonitoringQueueName());
		} catch (ExceptionMonitoringQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete monitoring queue name------");
			System.out.println(queueManager.deleteMonitoringQueueName());
		} catch (ExceptionMonitoringQueueNameNotFound | ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------reset monitoring queue name------");
			System.out.println(queueManager.resetMonitoringQueueName(newMonitoringQueueName));
		} catch (ExceptionQueueManagerInternalFailure | ExceptionMonitoringQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get monitoring queue name again------");
			System.out.println(queueManager.getMonitoringQueueName());
		} catch (ExceptionMonitoringQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete monitoring queue name------");
			System.out.println(queueManager.deleteMonitoringQueueName());
		} catch (ExceptionMonitoringQueueNameNotFound | ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void testCQAll() {
		//
		String controlQueueName1 = "C-Q-Name-C1";
		String controlQueueName2 = "C-Q-Name-C2";
		
		try {
			System.out.println("------get control queue names------");
			System.out.println(queueManager.getControlQueues());
		} catch (ExceptionControlQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names from DB------");
			System.out.println(queueManager.getControlQueuesFromDB());
		} catch (ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------add control queue name------");
			System.out.println(queueManager.addControlQueue(controlQueueName1));
		} catch (ExceptionQueueManagerInternalFailure | ExceptionControlQueueNameConflict e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names------");
			System.out.println(queueManager.getControlQueues());
		} catch (ExceptionControlQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names from DB------");
			System.out.println(queueManager.getControlQueuesFromDB());
		} catch (ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------add control queue name again1------");
			System.out.println(queueManager.addControlQueue(controlQueueName1));
		} catch (ExceptionQueueManagerInternalFailure | ExceptionControlQueueNameConflict e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------add control queue name again2------");
			System.out.println(queueManager.addControlQueue(controlQueueName2));
		} catch (ExceptionQueueManagerInternalFailure | ExceptionControlQueueNameConflict e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names------");
			System.out.println(queueManager.getControlQueues());
		} catch (ExceptionControlQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names from DB------");
			System.out.println(queueManager.getControlQueuesFromDB());
		} catch (ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete control queue name------");
			System.out.println(queueManager.deleteControlQueueName(controlQueueName1));
		} catch (ExceptionQueueManagerInternalFailure | ExceptionControlQueueNameNotFound e) {
			e.printStackTrace();
		}		
		
		try {
			System.out.println("------get control queue names------");
			System.out.println(queueManager.getControlQueues());
		} catch (ExceptionControlQueueNameNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------get control queue names from DB------");
			System.out.println(queueManager.getControlQueuesFromDB());
		} catch (ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("------delete control queue names from DB------");
			System.out.println(queueManager.deleteControlQueueNames());
		} catch (ExceptionQueueManagerInternalFailure e) {
			e.printStackTrace();
		}
	}
}