package com.example.code_judge.domain;
 
import jakarta.persistence.*;
import lombok.Data;
 
import java.time.LocalDateTime;
 
@Entity
@Data
public class KafkaEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String topic;
 
    @Lob
    private String message;

    private String status;
 
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
}