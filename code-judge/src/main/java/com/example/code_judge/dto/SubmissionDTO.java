package com.example.code_judge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class SubmissionDTO {

    @JsonProperty("submission_id") // JSON 필드 "submission_id"를 Java 필드 "submissionId"에 매핑
    private Long submissionId;

    @JsonProperty("problem_id")
    private Long problemId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("submitted_at")
    private LocalDateTime submittedAt; // LocalDateTime으로 변경

    private String language;
    private String status;

    // Getters and Setters
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
