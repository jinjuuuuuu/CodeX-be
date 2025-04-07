package com.example.code_judge.dto;

public class ProblemListDTO {
    private Long problemId;
    private String title;
    private Integer difficulty;
    private String tags;
    private Long totalSubmitted;
    private String totalAccuracy;

    public ProblemListDTO(Long problemId, String title, Integer difficulty, String tags, Long totalSubmitted, String totalAccuracy) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.tags = tags;
        this.totalSubmitted = totalSubmitted;
        this.totalAccuracy = totalAccuracy;
    }

    // Getters and Setters
    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getTotalSubmitted() {
        return totalSubmitted;
    }

    public void setTotalSubmitted(Long totalSubmitted) {
        this.totalSubmitted = totalSubmitted;
    }

    public String getTotalAccuracy() {
        return totalAccuracy;
    }

    public void setTotalAccuracy(String totalAccuracy) {
        this.totalAccuracy = totalAccuracy;
    }
}