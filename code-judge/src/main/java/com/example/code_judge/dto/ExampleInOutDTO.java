package com.example.code_judge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ExampleInOutDTO {

    @JsonProperty("input")
    private Map<String, Object> input; // JSON 객체와 매핑

    @JsonProperty("output")
    private Object output; // 다양한 데이터 타입을 받을 수 있도록 Object 사용

    public ExampleInOutDTO() {}

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }
}
