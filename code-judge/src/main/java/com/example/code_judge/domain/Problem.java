package com.example.code_judge.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id") // DB 컬럼명은 유지하고, Java에서는 problemId 사용
    private Long problemId;

    private String title;
    private String description;

    @OneToOne(cascade = CascadeType.ALL) // ExampleInOut과 연관관계 설정
    @JoinColumn(name = "example_inout") // DB에서 외래 키 컬럼 이름 지정
    private ExampleInOut exampleInOut;

    @Version
    private Integer version; // 낙관적 잠금을 위한 버전 필드

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}