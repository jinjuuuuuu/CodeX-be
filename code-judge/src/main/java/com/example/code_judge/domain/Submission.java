package com.example.code_judge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID
    @Column(name = "submission_id")
    private Long submissionId;

    @ManyToOne(fetch = FetchType.LAZY) // User와 다대일 관계 설정
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 설정
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // Problem과 다대일 관계 설정
    @JoinColumn(name = "problem_id", nullable = false) // 외래 키 설정
    private Problem problem;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String code;

    @NotNull
    @Column(length = 50)
    private String language;

    @NotNull
    @Column(length = 20)
    private String status; // pass 또는 fail 저장

    @NotNull
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt; // 제출 시간 저장

    public Submission(User user, Problem problem, String code, String language, String status, LocalDateTime submittedAt) {
        this.user = user;
        this.problem = problem;
        this.code = code;
        this.language = language;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    // 추가된 메서드
    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    public Long getProblemId() {
        return problem != null ? problem.getProblemId() : null;
    }
}