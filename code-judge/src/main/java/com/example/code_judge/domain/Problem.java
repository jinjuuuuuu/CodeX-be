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
    @Column(name = "problem_id") // DB 컬럼명 지정
    @JsonProperty("problem_id") // JSON 필드명 지정
    private Integer problemId;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Integer difficulty;

    @Column(length = 100)
    private String tag;

    @Lob
    @Column(columnDefinition = "TEXT")
    @JsonProperty("example_input")
    private String exampleInput; // 문자열로 변경

    @Lob
    @Column(columnDefinition = "TEXT")
    @JsonProperty("example_output")
    private String exampleOutput; // 문자열로 변경
}