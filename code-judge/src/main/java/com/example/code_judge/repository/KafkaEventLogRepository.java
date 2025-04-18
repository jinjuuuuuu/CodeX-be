package com.example.code_judge.repository;

import com.example.code_judge.domain.KafkaEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaEventLogRepository extends JpaRepository<KafkaEventLog, Long> {
}