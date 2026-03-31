package com.spring.ai.practice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama")
public class SingleModelController {

    //****First enable ChatClient from property file

        private ChatClient chatClient;


        public SingleModelController(ChatClient.Builder builder){
            this.chatClient = builder.build();
        }

        @GetMapping("/chat")
        public ResponseEntity<String> chat(
                @RequestParam(
                        value = "q",
                        required = true
                )
                String q
        ){
            var resultResponse = chatClient
                    .prompt(q)
                    .call()
                    .content();
            return new ResponseEntity<>(resultResponse, HttpStatus.OK);
        }
    }

