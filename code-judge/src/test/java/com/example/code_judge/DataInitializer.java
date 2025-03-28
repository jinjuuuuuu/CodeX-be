package com.example.code_judge;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
// import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DataInitializer(ProblemRepository problemRepository, SubmissionRepository submissionRepository, ObjectMapper objectMapper) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initData() {
        System.out.println("✅ DataInitializer 실행 시작!");
    
        // 데이터 중복 방지: 기존 데이터 삭제
        problemRepository.deleteAll();
        submissionRepository.deleteAll();
        System.out.println("✅ 기존 데이터 삭제 완료!");
    
        try {
            // test-data.json 파일 불러오기
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data.json");
            if (inputStream == null) {
                System.out.println("❌ test-data.json 파일을 찾을 수 없습니다.");
                return;
            }
    
            // JSON 데이터를 Java 객체 리스트로 변환
            TestData testData = objectMapper.readValue(inputStream, TestData.class);
    
            // 문제 데이터 저장
            testData.getProblems().forEach(problem -> {
                System.out.println("문제 저장: " + problem.getTitle());
                problemRepository.save(problem);
            });
    
            // 제출 데이터 저장
            testData.getSubmissions().forEach(submission -> {
                System.out.println("제출 저장: " + submission.getCode());
                submissionRepository.save(submission);
            });
    
            System.out.println("✅ 문제 및 제출 데이터가 H2 DB에 저장되었습니다!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ test-data.json 로드 실패", e);
        }
    }

    // JSON 파일을 매핑할 DTO 클래스 (내부 클래스)
    private static class TestData {
        private List<Problem> problems;
        private List<Submission> submissions;

        public List<Problem> getProblems() { return problems; }
        public List<Submission> getSubmissions() { return submissions; }
    }
}
