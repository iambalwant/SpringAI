package com.spring.ai.practice.service.Interface;

import com.spring.ai.practice.entity.Tut;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    String chat(String query);
    String chatTemplate();
    String advisor(String query);
    String chatMemory(String query, String userId);
    Flux<String> streamChat(String query);
}
