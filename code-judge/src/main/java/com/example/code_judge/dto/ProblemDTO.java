package com.example.code_judge.dto;

public class ProblemDTO {
    private Long problemId;
    private String title;
    private String description;
    private Integer difficulty;
    private String tags;
    private String exampleInput;
    private String exampleOutput;
    private Long totalSubmitted;
    private String totalAccuracy;

    public ProblemDTO(Long problemId, String title, String description, Integer difficulty, String tags, String exampleInput, String exampleOutput, Long totalSubmitted, String totalAccuracy) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.tags = tags;
        this.exampleInput = exampleInput;
        this.exampleOutput = exampleOutput;
        this.totalSubmitted = totalSubmitted;
        this.totalAccuracy = totalAccuracy;
    }
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    public String getExampleInput() {
        return exampleInput;
    }
    
    public void setExampleInput(String exampleInput) {
        this.exampleInput = exampleInput;
    }
    
    public String getExampleOutput() {
        return exampleOutput;
    }
    
    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
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
