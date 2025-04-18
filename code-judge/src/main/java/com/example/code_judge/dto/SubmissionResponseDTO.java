package com.example.code_judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionResponseDTO {
    private String message;
    private String status;

    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public SubmissionResponseDTO() {}
}