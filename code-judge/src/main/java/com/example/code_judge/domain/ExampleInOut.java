package com.example.code_judge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExampleInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_inout_id")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "example_input", joinColumns = @JoinColumn(name = "example_inout_id"))
    private List<String> exampleInput; // 여러 개의 입력을 저장

    @Lob
    private String exampleOutput; // 다양한 출력 데이터를 JSON 문자열로 저장
}
