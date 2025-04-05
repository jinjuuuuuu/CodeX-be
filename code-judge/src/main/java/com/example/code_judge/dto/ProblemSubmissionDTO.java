package com.example.code_judge.dto;

public class ProblemSubmissionDTO {
    private Long problemId;
    private String problemStatus;
    private Long problemAttempts;
    
    public ProblemSubmissionDTO(Long problemId, String problemStatus, Long problemAttempts) {
        this.problemId = problemId;
        this.problemStatus = problemStatus;
        this.problemAttempts = problemAttempts;
    }
    public Long getProblemId() {
        return problemId;
    }
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }
    public String getProblemStatus() {
        return problemStatus;
    }
    public void setProblemStatus(String problemStatus) {
        this.problemStatus = problemStatus;
    }
    public Long getProblemAttempts() {
        return problemAttempts;
    }
    public void setProblemAttempts(Long problemAttempts) {
        this.problemAttempts = problemAttempts;
    }
}
