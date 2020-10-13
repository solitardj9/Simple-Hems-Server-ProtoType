package com.lge.hemsServer.application.idMapManager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionIdMapManagerInternalFailure;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdConflict;
import com.lge.hemsServer.application.idMapManager.model.exception.ExceptionPrevIdNotFound;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IdMapManagerTest {

	@Autowired
	IdMapManager idMapManager;
	
	@Test
	public void testAll() {
		//
		String prevId_1 = "AAA";
		String nextId_1 = "AAA_II";
		
		String prevId_2 = "BBB";
		String nextId_2 = "BBB_II";
		
		try {
			System.out.println("add ids");
			System.out.println(idMapManager.addMappedId(prevId_1, nextId_1));
		} catch (ExceptionPrevIdConflict | ExceptionIdMapManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("re-add ids");
			System.out.println(idMapManager.addMappedId(prevId_1, nextId_1));
		} catch (ExceptionPrevIdConflict | ExceptionIdMapManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("add ids");
			System.out.println(idMapManager.addMappedId(prevId_2, nextId_2));
		} catch (ExceptionPrevIdConflict | ExceptionIdMapManagerInternalFailure e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("get ids");
			System.out.println(idMapManager.getMappedIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("delete id");
			System.out.println(idMapManager.deleteMappedId(prevId_1));
		} catch (ExceptionIdMapManagerInternalFailure | ExceptionPrevIdNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("get id");
			System.out.println(idMapManager.getMappedId(prevId_2));
		} catch (ExceptionPrevIdNotFound e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("get ids");
			System.out.println(idMapManager.getMappedIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}