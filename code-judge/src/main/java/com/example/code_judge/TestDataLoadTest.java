package com.example.code_judge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
public class TestDataLoadTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws IOException {
        // test-data.json 파일을 읽어서 객체로 변환
        TestDataDTO testData = objectMapper.readValue(
                new ClassPathResource("test-data.json").getFile(),
                TestDataDTO.class
        );

        // 문제 데이터 저장
        for (ProblemDTO problemDTO : testData.getProblems()) {
            Problem problem = new Problem();
            problem.setId(problemDTO.getId());
            problem.setTitle(problemDTO.getTitle());
            problem.setDescription(problemDTO.getDescription());
            problem.setInput(problemDTO.getInput());
            problem.setExpectedOutput(problemDTO.getExpectedOutput());
            problemRepository.save(problem);
        }

        // 제출 데이터 저장
        for (SubmissionDTO submissionDTO : testData.getSubmissions()) {
            Submission submission = new Submission();
            submission.setId(submissionDTO.getId());
            submission.setUserId(submissionDTO.getUserId());
            submission.setProblemId(submissionDTO.getProblemId());
            submission.setCode(submissionDTO.getCode());
            submission.setLanguage(submissionDTO.getLanguage());
            submission.setStatus(submissionDTO.getStatus());
            submissionRepository.save(submission);
        }
    }

    @Test
    public void testDataInsertion() {
        // 데이터 삽입 후, 데이터가 잘 들어갔는지 확인하는 테스트 코드 작성 가능
        System.out.println("Test data loaded successfully.");
    }
}
