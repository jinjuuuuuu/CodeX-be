package com.example.code_judge.domain;
 
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
import java.time.LocalDateTime;
 
@Getter
@Setter
@NoArgsConstructor
@Data
public class KafkaEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String topic;
 
    @Lob
    private String message;
 
    private LocalDateTime createdAt;
}