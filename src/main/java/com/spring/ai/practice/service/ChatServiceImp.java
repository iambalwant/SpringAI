package com.spring.ai.practice.service;

import com.spring.ai.practice.entity.Tut;
import com.spring.ai.practice.service.Interface.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class ChatServiceImp implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImp(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
//sending parameter with constructor or make in config or in prompt object or in yml/property file
//    public ChatServiceImp(ChatClient.Builder builder) {
//        this.chatClient = builder
//                .defaultOptions(OllamaChatOptions.builder()
//                        .temperature(0.3)
//                        .build())
//                .build();
//    }

    @Override
    public String chat(String query) {

         String prompt="Tell me all about Virat Kohali ?";

//        call the LLM for the response

//        ChatClient.ChatClientRequestSpec prompt(); will be called
//        String content = chatClient
//                .prompt()
//                .user(prompt)
//                .system("As an expert in cricket")
//                .call().content();


//        ChatClient.ChatClientRequestSpec prompt(Prompt prompt);will be used
//        Prompt prompt1 = new Prompt(query);
        //sending parameter with prompt
//        Prompt prompt1 = new Prompt(query, OllamaChatOptions.builder()
//                .temperature(0.3)
//                .build()
//        );


        //modify this prompt and extra things to prompt make it more interative
        Prompt prompt1 = new Prompt(query);

        String queryStr = "As an expert in coding and programming. always write program in java. Now reply for this questions : {query}";



        var entity = chatClient
                .prompt()
                .user(u-> u.text(queryStr).param("query", query)) // we use prompt template instant of this
                .call()
                .content();


        return entity;
    }

    @Override
    public String chatTemplate(){

        //first Step
        PromptTemplate build = PromptTemplate
                .builder()
                .template("What is {techName} ? tell me the example of {exampleName}")
                .build();

        //Render the template

        String renderedMessage = build.render(Map.of(
                "techName", "Spring",
                "exampleName", "Spring Boot"
        ));

        Prompt prompt = new Prompt(renderedMessage);

        return this.chatClient
                .prompt(prompt)
                .call()
                .content();
    }


}
