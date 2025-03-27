package com.example.code_judge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.transaction.Transactional;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.dto.SubmissionDTO;
import com.example.code_judge.dto.TestDataDTO;
import com.example.code_judge.dto.ExampleInOutDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestDataLoadTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        // ObjectMapper에 JavaTimeModule 등록
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules(); // 추가 모듈 자동 등록

        // JSON 파일 읽기 및 데이터 로드
        TestDataDTO testData = objectMapper.readValue(
            new ClassPathResource("test-data.json").getFile(),
            TestDataDTO.class
        );

        // 문제 데이터 저장
        testData.getProblems().forEach(problemDTO -> {
            Problem problem = new Problem();
            problem.setProblemId(problemDTO.getProblemId());
            problem.setTitle(problemDTO.getTitle());
            problem.setDescription(problemDTO.getDescription());
            problemRepository.save(problem);
        });

        // 제출 데이터 저장
        testData.getSubmissions().forEach(submissionDTO -> {
            Submission submission = new Submission();
            submission.setSubmissionId(submissionDTO.getSubmissionId());
            submission.setProblemId(submissionDTO.getProblemId());
            submission.setUserId(submissionDTO.getUserId());
            submission.setCode(submissionDTO.getCode());
            submission.setLanguage(submissionDTO.getLanguage());
            submission.setStatus(submissionDTO.getStatus());
            submission.setSubmittedAt(submissionDTO.getSubmittedAt().atZone(java.time.ZoneId.systemDefault()));
            submissionRepository.save(submission);
        });
    }

    // @Transactional
    // @Test
    // public void testProblemCount() {
    //     long count = problemRepository.count();
    //     assertEquals(2, count, "문제 데이터 개수가 일치하지 않습니다.");
    // }

    // @Transactional
    // @Test
    // public void testSubmissionCount() {
    //     long count = submissionRepository.count();
    //     assertEquals(2, count, "제출 데이터 개수가 일치하지 않습니다.");
    // }
}
