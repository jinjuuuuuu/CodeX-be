package com.example.code_judge.kafka;

import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CacheInvalidationListener {

    private final CacheManager cacheManager;

    public CacheInvalidationListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @KafkaListener(topics = "problem-cache-invalidation", groupId = "cache-invalidation-group")
    public void handleCacheInvalidationEvent(String message) {
        // 메시지 파싱 (예: {"problemId": 1})
        Long problemId = parseProblemIdFromMessage(message);

        // 캐시 무효화
        if (problemId != null) {
            if (cacheManager.getCache("allProblemsPaged") != null) {
                if (cacheManager.getCache("allProblemsPaged") != null) {
                    cacheManager.getCache("allProblemsPaged").clear();
                }
            }
            if (cacheManager.getCache("problemById") != null) {
                cacheManager.getCache("problemById").evict(problemId);
            }
        }
    }

    private Long parseProblemIdFromMessage(String message) {
        // JSON 메시지 파싱 로직 (간단한 예)
        try {
            return Long.valueOf(message.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}