package com.example.code_judge.controller;

import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<String> createSubmission(@RequestBody SubmissionRequestDTO submissionRequestDTO) {
        try {
            submissionService.saveSubmission(submissionRequestDTO);
            return ResponseEntity.ok("Submission saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving submission: " + e.getMessage());
        }
    }
}