package com.andreidodu.aibrochure.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Configuration
public class AiConfiguration {

    @Bean(name = "openAiChatModel")
    public OpenAiChatModel getModel(@Value("${com.andreidodu.company.brochure.openai-api-key}") String apiKey) {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(GPT_4_O_MINI)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

}
