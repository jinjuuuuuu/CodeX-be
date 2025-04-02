package com.example.code_judge.domain;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT를 위한 설정
    @Column(name = "problem_id", nullable = false, updatable = false) // DB 컬럼명 지정
    private Long problemId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String title;

    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Integer difficulty;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Lob
    @Column(columnDefinition = "TEXT")
    @JsonProperty("example_input")
    private String exampleInput; // 문자열로 변경

    @Lob
    @Column(columnDefinition = "TEXT")
    @JsonProperty("example_output")
    private String exampleOutput; // 문자열로 변경
}