package com.example.code_judge.dto;

public class SubmissionRequestDTO {
    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private String status;

    // 기존 getter 및 setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // 추가된 getter 및 setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}