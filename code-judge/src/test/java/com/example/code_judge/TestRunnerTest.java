package com.example.code_judge;

import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.SubmissionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.code_judge.evaluator.TestRunner; // Adjust the package path if necessary

import java.util.List;

@SpringBootTest(classes = CodeJudgeApplication.class)
public class TestRunnerTest {

    @Autowired
    private TestRunner testRunner;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Test
    public void testRunTest() {
        // Given: 테스트 데이터 준비
        String email = "test@example.com";
        String code = "System.out.println(42);";
        String language = "java";
        Integer problemId = 1;

        // When: runTest 메서드 실행
        testRunner.runTest(email, code, language, problemId);

        // Then: submission 테이블에 데이터가 저장되었는지 확인
        List<Submission> submissions = submissionRepository.findAll();
        Assertions.assertFalse(submissions.isEmpty(), "Submission should be saved");

        // 저장된 데이터 확인
        Submission savedSubmission = submissions.get(0);
        Assertions.assertEquals(email, savedSubmission.getEmail());
        Assertions.assertEquals(code, savedSubmission.getCode());
        Assertions.assertEquals(language, savedSubmission.getLanguage());
        Assertions.assertEquals("fail", savedSubmission.getStatus()); // 예제에서는 기본적으로 실패로 가정
    }
}