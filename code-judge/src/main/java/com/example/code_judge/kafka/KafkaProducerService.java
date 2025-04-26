package com.example.code_judge.kafka;

import com.example.code_judge.dto.SubmissionRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, SubmissionRequestDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, SubmissionRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @Async
    public void sendSubmission(String topic, SubmissionRequestDTO submissionRequestDTO) {
        try {
            kafkaTemplate.send(topic, submissionRequestDTO)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("[DEBUG] SubmissionRequestDTO 메시지가 Kafka로 전송되었습니다: " + submissionRequestDTO);
                    } else {
                        System.err.println("[ERROR] 메시지 전송 실패: topic=" + topic + ", error=" + ex.getMessage());
                    }
                });
        } catch (Exception e) {
            System.err.println("[ERROR] Kafka 메시지 전송 중 오류 발생: " + e.getMessage());
        }
    }
}