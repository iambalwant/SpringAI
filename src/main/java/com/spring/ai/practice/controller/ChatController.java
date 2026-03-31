package com.spring.ai.practice.controller;


import com.spring.ai.practice.entity.Tut;
import com.spring.ai.practice.service.ChatServiceImp;
import com.spring.ai.practice.service.Interface.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/multiModel")
public class ChatController {


    private ChatService chatService;

    public ChatController(
                          ChatService chatService
    ) {
        this.chatService = chatService;
    }
//    public ChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClinet,
//                          @Qualifier("ollamaAiChatClient") ChatClient ollamaAiChatClinet){
//        this.openAiChatClinet = openAiChatClinet;
//        this.ollamaAiChatClinet = ollamaAiChatClinet;
//    }

// Because we created a Chatclient config file to handle this
//    public ChatController(
//            OpenAiChatModel openAiChatModel,
//            OllamaChatModel ollamaChatModel
//    ){
//        this.openAiChatClinet=ChatClient.builder(openAiChatModel).build();
//        this.ollamaAiChatClinet=ChatClient.builder(ollamaChatModel).build();
//    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestParam(
                    value = "q",
                    required = true
            )
            String q
    ){
           return new ResponseEntity<>(chatService.advisor(q), HttpStatus.OK);
    }


    @GetMapping("/stream-chat")
    public ResponseEntity<Flux<String>> streamChat(
            @RequestParam("q") String query
    ){
        return new ResponseEntity<>(this.chatService.streamChat(query), HttpStatus.OK);
    }
}
