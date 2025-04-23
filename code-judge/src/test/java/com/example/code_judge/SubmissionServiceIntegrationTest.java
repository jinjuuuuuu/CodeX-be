package com.example.code_judge;

import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.service.SubmissionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class SubmissionServiceIntegrationTest {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionService submissionService;

    @Test
    void testSubmitCodeIntegration() {
        submissionRepository.deleteAll(); // 이전 데이터 삭제
        // Given: 테스트 데이터 준비
        SubmissionRequestDTO request = new SubmissionRequestDTO();
        request.setUserId(1L); // APP_USER 테이블에 user_id = 1 데이터가 있어야 함
        request.setProblemId(1L); // PROBLEM 테이블에 problem_id = 1 데이터가 있어야 함
        request.setCode("print(\"Hello World\")");
        request.setLanguage("python");

        // When: submitCode 메서드 호출
        String result = submissionService.submitCode(request);

        // Then: 결과 검증
        Assertions.assertEquals("fail", result, "SubmissionService should return 'Success'");

        // 데이터베이스에 저장된 Submission 확인
        List<Submission> submissions = submissionRepository.findAll();
        Assertions.assertEquals(1, submissions.size(), "Submission count should be 1");

        Submission savedSubmission = submissions.get(0);
        Assertions.assertEquals(1L, savedSubmission.getUser().getUserId(), "User ID should match");
        Assertions.assertEquals(1L, savedSubmission.getProblem().getProblemId(), "Problem ID should match");
        Assertions.assertEquals("print(\"Hello World\")", savedSubmission.getCode(), "Code should match");
        Assertions.assertEquals("python", savedSubmission.getLanguage(), "Language should match");
    }
}