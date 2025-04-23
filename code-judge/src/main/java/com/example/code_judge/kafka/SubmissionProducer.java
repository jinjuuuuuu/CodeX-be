package com.example.code_judge.kafka;

import com.example.code_judge.dto.SubmissionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubmissionProducer {

    private static final String TOPIC = "submission-topic";

    @Autowired
    private KafkaTemplate<String, SubmissionRequestDTO> kafkaTemplate;

    public void sendSubmission(SubmissionRequestDTO submissionRequestDTO) {
        kafkaTemplate.send(TOPIC, submissionRequestDTO);
        System.out.println("[DEBUG] SubmissionRequestDTO 메시지가 Kafka로 전송되었습니다.");
    }
}