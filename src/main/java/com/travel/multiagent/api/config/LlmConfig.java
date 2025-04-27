package com.travel.multiagent.api.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

    @Bean
    public ChatMemoryStore chatMemoryStore() {
        // OR RedisChatMemoryStore, PostgresMemoryStore, etc.
        return new InMemoryChatMemoryStore();
    }

    @Bean
    ChatMemoryProvider chatMemoryProvider(ChatMemoryStore chatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(chatMemoryStore)
                .maxMessages(30)
                .build();
    }
}
