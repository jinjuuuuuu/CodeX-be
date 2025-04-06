package com.example.code_judge.dto;

import lombok.Data;

@Data
public class SubmissionRequestDTO {
    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private String status; // pass or fail
}