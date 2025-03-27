package com.example.code_judge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProblemDTO {

    @JsonProperty("problem_id")
    private Long problemId;

    private String title;
    private String description;

    @JsonProperty("example_inout") // JSON 필드 이름 매핑
    private ExampleInOutDTO exampleInOut;

    public ProblemDTO() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExampleInOutDTO getExampleInOut() {
        return exampleInOut;
    }

    public void setExampleInOut(ExampleInOutDTO exampleInOut) {
        this.exampleInOut = exampleInOut;
    }
}
