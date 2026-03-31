package com.spring.ai.practice.service;

import com.spring.ai.practice.entity.Tut;
import com.spring.ai.practice.service.Interface.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;


@Component
public class ChatServiceImp implements ChatService {

    private final ChatClient chatClient;


    @Value("classpath:/prompts/User-message.st")
    private Resource userMessage;

    @Value("classpath:/prompts/System-message.st")
    private Resource systemMessage;

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
//Chatclinet
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

//PromptTemplate
    //In Mockito Test - not controller
    @Override
    public String chatTemplate(){

//        A PromptTemplate is the dynamic input you send as a user.
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

        //another Method - using systemPromptTemplate -A System Prompt Template defines the behavior, tone, and rules of the AI.

        var systemPromptTemplate = SystemPromptTemplate
                .builder()
                .template("You are an senior backend Engineer.")
                .build();

        var systemMessage = systemPromptTemplate.createMessage();

        var userPromptTemplate = PromptTemplate.builder()
                .template("What is {techName} ? tell me the example of {exampleName}")
                .build();
        var userMessage = userPromptTemplate.createMessage(Map.of(
                "techName", "Spring",
                "exampleName", "Spring Boot"
        ));

        //we need to combine system and user prompt

        Prompt prompt1 = new Prompt(systemMessage, userMessage);

//        return this.chatClient
//                .prompt(prompt1)
//                .call()
//                .content();

        //Another Method using fluent ChatClient

        return chatClient.prompt()
                .system(system -> system.text("You are an senior backend Engineer."))
//                .user(user -> user.text("What is {techName} ? tell me the example of {exampleName}")
//                        .param("techName", "Spring")
//                        .param("exampleName", "Spring Boot"))
                .user(user -> user.text(this.userMessage).param("techName", "Spring").param("exampleName", "Spring Boot"))
                .call()
                .content();


    }

//Advisor
    @Override
    public String advisor(String query){
        return chatClient
                .prompt()
//                .advisors(new SimpleLoggerAdvisor()) ~ this way or in bean
                .system(system -> system.text(this.systemMessage))
//                .user(user -> user.text("What is {techName} ? tell me the example of {exampleName}")
//                        .param("techName", "Spring")
//                        .param("exampleName", "Spring Boot"))
                .user(user -> user.text(this.userMessage).param("Question", query))
                .call()
                .content();
    }

    @Override
    public Flux<String> streamChat(String query) {

         return this.chatClient.prompt()
                 .system(system -> system.text(
                         this.systemMessage
                 ))
                 .user(user->user.text(this.userMessage).param("Question",query))
                 .stream()
                 .content();

    }
}
