package com.example.code_judge.dto;

public class ProblemListDTO {
    private Integer problemId;
    private String title;
    private Integer difficulty;
    private String tag;

    public ProblemListDTO(Integer problemId, String title, Integer difficulty, String tag) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.tag = tag;
    }

    // Getters and Setters
    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}