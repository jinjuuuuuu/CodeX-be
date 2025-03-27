package com.example.code_judge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id") // DB 컬럼명은 submission_id
    private Long submissionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "problem_id")
    private Long problemId;

    private String code;
    private String language;
    private String status;

    @Column(name = "submitted_at")
    private ZonedDateTime submittedAt;

    // Optional: 낙관적 잠금을 위한 버전 필드
    @Version
    private Integer version;

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSubmittedAt(ZonedDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
