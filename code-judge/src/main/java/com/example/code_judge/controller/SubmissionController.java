package com.example.code_judge.controller;

import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.dto.SubmissionResponseDTO;
import com.example.code_judge.service.SubmissionService;
import com.example.code_judge.domain.Submission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponseDTO> createSubmission(@RequestBody SubmissionRequestDTO submissionRequestDTO) {
        try {
            // 제출 저장 및 결과 평가
            Submission submission = submissionService.saveSubmission(submissionRequestDTO);

            // JSON 형식으로 상태와 메시지를 반환
            SubmissionResponseDTO response = new SubmissionResponseDTO(
                submission.getStatus(),
                "Submission saved successfully!"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            SubmissionResponseDTO errorResponse = new SubmissionResponseDTO(
                "error",
                "Error saving submission: " + e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}