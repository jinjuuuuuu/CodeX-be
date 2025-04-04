package com.example.code_judge.dto;

public class ProblemListDTO {
    private Long problemId;
    private String title;
    private Integer difficulty;
    private String tags;

    public ProblemListDTO(Long problemId, String title, Integer difficulty, String tags) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.tags = tags;
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
}