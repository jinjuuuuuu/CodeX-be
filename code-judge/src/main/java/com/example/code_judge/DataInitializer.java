package com.example.code_judge;

import com.example.code_judge.domain.Problem;
// import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
// import com.example.code_judge.repository.SubmissionRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer {
    private final ProblemRepository problemRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DataInitializer(ProblemRepository problemRepository, ObjectMapper objectMapper) {
        this.problemRepository = problemRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initData() {
        System.out.println("✅ DataInitializer 실행 시작!");

        try {
            // test-data.json 파일 불러오기
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data.json");
            if (inputStream == null) {
                System.out.println("❌ test-data.json 파일을 찾을 수 없습니다.");
                return;
            }

            // JSON 데이터를 Java 객체 리스트로 변환
            TestData testData = objectMapper.readValue(inputStream, TestData.class);

            // 문제 데이터 처리
        long existingProblemCount = problemRepository.count();
        long newProblemCount = testData.getProblems().size();

        if (existingProblemCount != newProblemCount) {
            System.out.println("✅ 문제 데이터 변경 감지: 기존 데이터를 업데이트하거나 새로 삽입합니다.");

            testData.getProblems().forEach(problemData -> {
                System.out.println("문제 처리 중: " + problemData.getTitle() + ", 난이도: " + problemData.getDifficulty());
                try {
                    // 문제 ID로 기존 문제를 찾음
                    Problem existingProblem = problemRepository.findById(problemData.getProblemId()).orElse(null);

                    if (existingProblem != null) {
                        // 기존 문제 업데이트
                        existingProblem.setTitle(problemData.getTitle());
                        existingProblem.setDescription(problemData.getDescription());
                        existingProblem.setDifficulty(problemData.getDifficulty());
                        existingProblem.setTag(problemData.getTag());
                        existingProblem.setExampleInput(problemData.getExampleInput());
                        existingProblem.setExampleOutput(problemData.getExampleOutput());
                        problemRepository.save(existingProblem);
                        System.out.println("✅ 기존 문제 업데이트 완료: " + problemData.getTitle());
                    } else {
                        // 새로운 문제 삽입
                        problemRepository.save(problemData);
                        System.out.println("✅ 새 문제 삽입 완료: " + problemData.getTitle());
                    }
                } catch (Exception e) {
                    System.err.println("❌ 문제 처리 실패: " + problemData.getTitle());
                    e.printStackTrace();
                }
            });
            problemRepository.flush();
        } else {
            System.out.println("✅ 문제 데이터 변경 없음: 기존 데이터를 유지합니다.");
        }

            // 제출 데이터는 삭제하지 않고 유지
            System.out.println("✅ 제출 데이터는 유지됩니다.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ test-data.json 로드 실패", e);
        }
    }

    // JSON 파일을 매핑할 DTO 클래스 (내부 클래스)
    private static class TestData {
        private List<Problem> problems;

        public List<Problem> getProblems() {
            return problems;
        }
    }
}