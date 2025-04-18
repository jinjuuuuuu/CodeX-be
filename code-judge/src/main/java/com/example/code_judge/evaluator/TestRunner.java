package com.example.code_judge.evaluator;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.User;
import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.dto.SubmissionResponseDTO;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class TestRunner implements CommandLineRunner {
    private final CodeExecutor codeExecutor;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    public TestRunner(CodeExecutor codeExecutor) {
        this.codeExecutor = codeExecutor;
    }

    public void runTest(Long userId, String code, String language, Long problemId) {
        System.out.println("[DEBUG] 입력값 - user_id: " + userId + ", language: " + language + ", problemId: " + problemId);

        // User 객체 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                System.err.println("[ERROR] User not found with id: " + userId);
                return new IllegalArgumentException("User not found with id: " + userId);
            });
        System.out.println("[DEBUG] User: " + user);

        // Problem 객체 조회
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> {
                System.err.println("[ERROR] Problem not found with id: " + problemId);
                return new IllegalArgumentException("Problem not found with id: " + problemId);
            });

        // 제출된 코드를 실행하고 결과를 가져옴
        System.out.println("[DEBUG] Example Input: " + (problem.getExampleInput() != null ? problem.getExampleInput() : "null"));
        System.out.println("[DEBUG] Example Output: " + (problem.getExampleOutput() != null ? problem.getExampleOutput() : "null"));

        String actualOutput;
        try {
            actualOutput = codeExecutor.execute(code, problem.getExampleInput(), language);
            System.out.println("[DEBUG] Actual Output: " + (actualOutput != null ? actualOutput : "null"));
        } catch (Exception e) {
            System.err.println("[ERROR] 코드 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 테스트 결과 비교
        boolean isPassed = Objects.equals(actualOutput, problem.getExampleOutput());
        System.out.println(isPassed ? "[DEBUG] 테스트 통과" : "[DEBUG] 테스트 실패 - Expected: " + problem.getExampleOutput() + ", Actual: " + actualOutput);

        // SubmissionRequestDTO 생성
        SubmissionRequestDTO submissionRequestDTO = new SubmissionRequestDTO();
        submissionRequestDTO.setUserId(userId);
        submissionRequestDTO.setProblemId(problemId);
        submissionRequestDTO.setCode(code);
        submissionRequestDTO.setLanguage(language);

        // API 호출을 통해 Submission 저장
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:8080/submissions"; // SubmissionController의 API URL

            // API 호출 및 응답 처리
            ResponseEntity<SubmissionResponseDTO> response = restTemplate.postForEntity(apiUrl, submissionRequestDTO, SubmissionResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                SubmissionResponseDTO responseBody = response.getBody();
                System.out.println("[DEBUG] Submission 저장 성공 - Status: " + responseBody.getStatus() + ", Message: " + responseBody.getMessage());
            } else {
                System.err.println("[ERROR] Submission 저장 실패 - 응답 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Submission 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
    }
}