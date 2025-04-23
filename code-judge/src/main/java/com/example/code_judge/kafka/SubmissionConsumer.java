package com.example.code_judge.kafka;

import com.example.code_judge.domain.Submission;
import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.evaluator.CodeExecutor;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SubmissionConsumer {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @KafkaListener(topics = "submission-topic", groupId = "code-consumer-group")
    @Transactional
    public void consumeSubmission(SubmissionRequestDTO submissionRequestDTO) {
        System.out.println("[DEBUG] Kafka에서 SubmissionRequestDTO 메시지를 수신했습니다: " + submissionRequestDTO);

        if (submissionRequestDTO == null) {
            System.err.println("[ERROR] 수신된 SubmissionRequestDTO가 null입니다");
            return;
        }

        try {
            // User 및 Problem 조회
            var user = userRepository.findById(submissionRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + submissionRequestDTO.getUserId()));
            var problem = problemRepository.findById(submissionRequestDTO.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + submissionRequestDTO.getProblemId()));

            // 코드 실행
            CodeExecutor codeExecutor = new CodeExecutor();
            String executionResult = codeExecutor.execute(submissionRequestDTO.getCode(), problem.getExampleInput(), submissionRequestDTO.getLanguage());

            // 결과 저장
            boolean isPassed = executionResult.trim().equals(problem.getExampleOutput().trim());
            String status = isPassed ? "pass" : "fail";

            Submission submission = submissionRepository.findById(submissionRequestDTO.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionRequestDTO.getSubmissionId()));

            submission.setStatus(status);
            submission.setActualOutput(executionResult);
            submission.setSubmittedAt(LocalDateTime.now()); // 업데이트 시간 추가
            submissionRepository.save(submission);

            System.out.println("[DEBUG] Submission 처리 완료: " + submission);

        } catch (Exception e) {
            System.err.println("[ERROR] Submission 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}