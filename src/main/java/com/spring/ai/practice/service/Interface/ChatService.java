package com.spring.ai.practice.service.Interface;

import com.spring.ai.practice.entity.Tut;

import java.util.List;

public interface ChatService {

    String chat(String query);
    String chatTemplate();
    String advisor(String queary);
}
