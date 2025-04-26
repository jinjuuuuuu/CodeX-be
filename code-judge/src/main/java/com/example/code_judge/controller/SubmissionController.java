package com.example.code_judge.controller;

import com.example.code_judge.domain.Submission;
import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.User;
import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.dto.SubmissionResponseDTO;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.repository.UserRepository;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.kafka.KafkaProducerService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<SubmissionResponseDTO> createSubmission(@RequestBody SubmissionRequestDTO submissionRequestDTO) {
        try {
            // User와 Problem 엔티티 조회
            User user = userRepository.findByUserId(submissionRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + submissionRequestDTO.getUserId()));
            Problem problem = problemRepository.findById(submissionRequestDTO.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + submissionRequestDTO.getProblemId()));

            // Submission 엔티티 생성 및 저장
            Submission submission = new Submission();
            submission.setUser(user);
            submission.setProblem(problem);
            submission.setCode(submissionRequestDTO.getCode());
            submission.setLanguage(submissionRequestDTO.getLanguage());
            submission.setStatus("pending"); // 초기 상태는 "pending"
            submission.setSubmittedAt(java.time.LocalDateTime.now());
            submissionRepository.save(submission);

            logger.info("Submission 저장 완료: {}", submission);
            
            // Kafka로 전송할 SubmissionRequestDTO에 submissionId 설정
            submissionRequestDTO.setSubmissionId(submission.getSubmissionId());
            kafkaProducerService.sendSubmission("submission-topic", submissionRequestDTO);


            // 클라이언트에 "pending" 상태 반환
            SubmissionResponseDTO response = new SubmissionResponseDTO(
                submission.getSubmissionId(),
                "Submission request received successfully!",
                "pending",
                null // Replace null with an empty string or appropriate default value
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Submission 생성 중 오류 발생: {}", e.getMessage(), e);
            SubmissionResponseDTO errorResponse = new SubmissionResponseDTO(
                null,
                "Error creating submission: " + e.getMessage(),
                "error",
                null
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionResult(@PathVariable Long submissionId) {
        try {
            // Submission 엔티티 조회
            Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionId));

            // 결과 반환
            SubmissionResponseDTO response = new SubmissionResponseDTO(
                "Submission result retrieved successfully!",
                submission.getStatus(),
                submission.getActualOutput()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Submission not found: {}", submissionId);
            return ResponseEntity.status(404).body(new SubmissionResponseDTO(
                e.getMessage(),
                "error",
                null
            ));
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new SubmissionResponseDTO(
                "An unexpected error occurred: " + e.getMessage(),
                "error",
                null
            ));
        }
    }
}