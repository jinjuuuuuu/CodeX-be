package com.example.code_judge.evaluator;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import com.example.code_judge.CodeJudgeApplication;

import java.util.Objects;
import java.util.Scanner;

@Component
public class TestRunner {
    private final CodeExecutor codeExecutor;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public TestRunner(CodeExecutor codeExecutor) {
        this.codeExecutor = codeExecutor;
    }

    public void runTest(String email, String code, String language, Integer problemId) {
        System.out.println("[DEBUG] 입력값 - email: " + email + ", language: " + language + ", problemId: " + problemId);
    
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
            email,
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

    // main 메서드 수정
    public static void main(String[] args) {
        // Spring Context 초기화
        ApplicationContext context = SpringApplication.run(CodeJudgeApplication.class, args);

        // TestRunner 빈 가져오기
        TestRunner testRunner = context.getBean(TestRunner.class);

        // 사용자 입력을 받기 위한 Scanner 객체 생성
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueSolving = true;
            // 문제 번호 입력 받기

            while(continueSolving) {
                System.out.print("Enter the problem ID you want to solve: ");
                int problemId = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                // 사용자로부터 email 입력 받기
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();

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
                    testRunner.runTest(email, code, language, problemId);
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