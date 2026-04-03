package com.spring.ai.practice.config;


import com.spring.ai.practice.advisors.TokenPrintAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    Logger logger = LoggerFactory.getLogger(AiConfig.class);

    //we can do this way to set parameters
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory){

        this.logger.info("Chat memory :" + chatMemory.getClass().getName());

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        return builder
                .defaultAdvisors(messageChatMemoryAdvisor,
                                 new TokenPrintAdvisor(),
                                 new SimpleLoggerAdvisor(),
                                 new SafeGuardAdvisor(List.of("porn"))
                )
                .defaultSystem("you are a helpful as coding assistant. you are expert in backend engineering")
                .defaultOptions(
                        OllamaChatOptions.builder()
                                .temperature(0.3)
                                .build()
                )
                .build();
    }

//    @Bean(name = "openAiChatClient")
//    public ChatClient openAiChatModel(OpenAiChatModel chatModel){
//        return ChatClient.builder(chatModel).build();
//    }
//
//    @Bean(name = "ollamaAiChatClient")
//    public ChatClient ollamaChatModel(OllamaChatModel chatModel){
//         return ChatClient.builder(chatModel).build();
//    }




}
