package com.spring.ai.practice;

import com.spring.ai.practice.helper.Helper;
import com.spring.ai.practice.service.Interface.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ChatService chatService;

	@Test
	void restTemplateRender(){
		System.out.println("Template Renderer");

		var output = this.chatService.chatTemplate();
		System.out.println(output);
	}

	@Test
    void saveDataToVectorDatabase(){
		System.out.println("saving data to database");
		this.chatService.saveData(Helper.getData());
		System.out.println("saving data to database done");

	}

}
