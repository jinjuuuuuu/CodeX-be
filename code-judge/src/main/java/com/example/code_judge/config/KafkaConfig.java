package com.example.code_judge.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic problemCacheInvalidationTopic() {
        return new NewTopic("problem-cache-invalidation", 1, (short) 1);
    }
}