package com.example.code_judge.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.code_judge.domain.KafkaEventLog;
import com.example.code_judge.repository.KafkaEventLogRepository;

import java.util.List;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaEventLogRepository kafkaEventLogRepository;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaEventLogRepository kafkaEventLogRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaEventLogRepository = kafkaEventLogRepository;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void sendPendingMessages() {
        List<KafkaEventLog> pendingEvents = kafkaEventLogRepository.findAll();

        for (KafkaEventLog event : pendingEvents) {
            kafkaTemplate.send(event.getTopic(), event.getMessage());
        }
    }
}