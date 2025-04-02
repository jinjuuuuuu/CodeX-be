package com.example.code_judge.dto;

public class ProblemDTO {
    private Integer problemId;
    private String title;
    private String description;
    private Integer difficulty;
    private String tag;
    private String exampleInput;
    private String exampleOutput;

    public ProblemDTO (Integer problemId, String title, String description, Integer difficulty, String tag, String exampleInput, String exampleOutput) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.tag = tag;
        this.exampleInput = exampleInput;
        this.exampleOutput = exampleOutput;
    }
    
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
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
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
}
