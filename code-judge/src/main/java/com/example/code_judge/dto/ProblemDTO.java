package com.example.code_judge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProblemDTO {
    private int id;
    private String title;
    private String description;
    private Object input;  // JSON에서 숫자나 객체로 올 수 있으므로 Object로 처리
    private Object expectedOutput;  // 예상 출력

    // Getters and Setters
}