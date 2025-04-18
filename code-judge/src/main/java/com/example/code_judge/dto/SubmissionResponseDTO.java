package com.example.code_judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionResponseDTO {
    private String message;
    private String status;
    private String actualOutput;

    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getActualOutput() {
        return actualOutput;
    }
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }   
    public SubmissionResponseDTO() {}
}