package com.spring.ai.practice.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;


public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {

    private Logger logger = LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        this.logger.info("my Token Print Advisor");

        this.logger.info("Request " + chatClientRequest.prompt().getContents());

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        this.logger.info("Responce Recive from the model ");
        this.logger.info("Responce: "+ chatClientResponse.chatResponse().getResult().getOutput().getText());
        this.logger.info("Total Token consume: "+chatClientResponse.chatResponse().getMetadata().getUsage().getTotalTokens());

        return chatClientResponse;
    }


    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {

        Flux<ChatClientResponse> chatClientResponseFlux = streamAdvisorChain.nextStream(chatClientRequest);

        return chatClientResponseFlux;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
