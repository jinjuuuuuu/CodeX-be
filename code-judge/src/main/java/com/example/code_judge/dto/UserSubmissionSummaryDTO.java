package com.example.code_judge.dto;

public class UserSubmissionSummaryDTO {
    private Long userId;
    private Long userSubmissions;
    private Long passCount;
    private Long failCount;
    private Double passRate;

    public UserSubmissionSummaryDTO(Long userId, Long userSubmissions, Long passCount, Long failCount, Double passRate) {
        this.userId = userId;
        this.userSubmissions = userSubmissions;
        this.passCount = passCount;
        this.failCount = failCount;
        this.passRate = passRate;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserSubmissions() {
        return userSubmissions;
    }

    public void setTotalSubmissions(Long userSubmissions) {
        this.userSubmissions = userSubmissions;
    }

    public Long getPassCount() {
        return passCount;
    }

    public void setPassCount(Long passCount) {
        this.passCount = passCount;
    }

    public Long getFailCount() {
        return failCount;
    }

    public void setFailCount(Long failCount) {
        this.failCount = failCount;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }    
}
