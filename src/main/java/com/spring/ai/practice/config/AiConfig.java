package com.spring.ai.practice.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
//import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    //we can do this way to set parameters
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
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
