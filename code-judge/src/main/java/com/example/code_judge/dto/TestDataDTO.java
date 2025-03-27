package com.example.code_judge.dto;

import java.util.List;

public class TestDataDTO {

    private List<ProblemDTO> problems;  // 문제 목록
    private List<SubmissionDTO> submissions;  // 제출 목록

    // Getter와 Setter 추가
    public List<ProblemDTO> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemDTO> problems) {
        this.problems = problems;
    }

    public List<SubmissionDTO> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<SubmissionDTO> submissions) {
        this.submissions = submissions;
    }
}
