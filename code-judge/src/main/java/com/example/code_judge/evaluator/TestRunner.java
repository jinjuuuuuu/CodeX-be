package com.example.code_judge.evaluator;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.repository.UserRepository;
import com.example.code_judge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Objects;
import java.util.Scanner;

@Component
public class TestRunner implements CommandLineRunner {
    private final CodeExecutor codeExecutor;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

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
        String status = isPassed ? "pass" : "fail";
        System.out.println(isPassed ? "[DEBUG] 테스트 통과" : "[DEBUG] 테스트 실패 - Expected: " + problem.getExampleOutput() + ", Actual: " + actualOutput);

        // Submission 객체 생성 및 저장
        System.out.println("[DEBUG] status: " + status);

        Submission submission = new Submission(
            user,
            problem,
            code,
            language,
            status,
            java.time.LocalDateTime.now()
        );
    
        try {
            submissionRepository.save(submission);
            System.out.println("[DEBUG] Submission 저장 성공 - " + submission);
        } catch (Exception e) {
            System.err.println("[ERROR] Submission 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void run(String... args) {
        // 사용자 입력을 받기 위한 Scanner 객체 생성
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueSolving = true;

            while (continueSolving) {
                // 사용자로부터 userId 입력 받기
                System.out.print("Enter your user ID: ");
                long userId = scanner.nextLong();
                scanner.nextLine(); // 버퍼 비우기

                // 문제 번호 입력 받기
                System.out.print("Enter the problem ID you want to solve: ");
                long problemId = scanner.nextLong();
                scanner.nextLine(); // 버퍼 비우기

                // 프로그래밍 언어 입력 받기
                System.out.print("Enter the programming language (e.g., java, python, c, cpp): ");
                String language = scanner.nextLine();

                // 사용자로부터 code 입력 받기
                System.out.println("Enter your code (end with an empty line): ");
                StringBuilder codeBuilder = new StringBuilder();
                while (true) {
                    String line = scanner.nextLine();
                    if (line.isEmpty()) break; // 빈 줄 입력 시 종료
                    codeBuilder.append(line).append("\n");
                }
                String code = codeBuilder.toString();

                // 테스트 실행
                try {
                    runTest(userId, code, language, problemId);
                } catch (Exception e) {
                    System.err.println("Error occurred during test execution: " + e.getMessage());
                }

                System.out.println("Continue solving other problems? (yes/no)");
                String response = scanner.nextLine();
                if (!response.equals("yes")) {
                    continueSolving = false;
                }
            }
        }
    }
}