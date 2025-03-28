package com.example.code_judge.dto;

import com.example.code_judge.domain.ExampleInOut;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleInOutDTO {
    private Map<String, Object> input; // JSON에서 다양한 형태의 입력을 처리
    private Object output; // JSON에서 다양한 형태의 출력을 처리

    // Getters and Setters
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

    // DTO를 엔티티로 변환하는 메서드
    public ExampleInOut toEntity() {
        ExampleInOut exampleInOut = new ExampleInOut();

        // input의 value만 추출하여 List<String>으로 변환
        if (input != null) {
            List<String> inputValues = input.values().stream()
                .map(Object::toString) // value를 String으로 변환
                .collect(Collectors.toList());
            exampleInOut.setExampleInput(inputValues);
        }

        // output을 String으로 변환
        if (output != null) {
            exampleInOut.setExampleOutput(output.toString());
        }

        return exampleInOut;
    }
}
