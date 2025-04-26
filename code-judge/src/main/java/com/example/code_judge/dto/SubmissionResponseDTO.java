package com.example.code_judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SubmissionResponseDTO {
    private Long submissionId;
    private String message;
    private String status;
    private String actualOutput;
    
    public SubmissionResponseDTO(String message, String status, String actualOutput) {
        this.message = message;
        this.status = status;
        this.actualOutput = actualOutput;
    }
}