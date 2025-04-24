package com.travelconcierge.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

    @Value("${langchain.llm-provider}")
    private String provider;

    @Value("${openai.api-key}")
    private String openaiApiKey;

    @Value("${openai.model-name}")
    private String openaiModel;

    @Value("${ollama.base-url}")
    private String ollamaBaseUrl;

    @Value("${ollama.model-name}")
    private String ollamaModel;

    @Bean
    public ChatLanguageModel llm() {
        return switch (provider.toLowerCase()) {
            case "openai" -> createOpenAiChatModel();
            case "ollama" -> createOllamaChatModel();
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

    private ChatLanguageModel createOpenAiChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(openaiModel)
                .build();
    }

    private ChatLanguageModel createOllamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModel)
                .build();
    }
}
