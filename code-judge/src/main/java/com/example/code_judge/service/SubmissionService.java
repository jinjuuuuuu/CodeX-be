package com.example.code_judge.service;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.domain.Submission;
import com.example.code_judge.domain.User;
import com.example.code_judge.dto.SubmissionRequestDTO;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import com.example.code_judge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;

    public void saveSubmission(SubmissionRequestDTO submissionRequestDTO) {
        // User 조회
        User user = userRepository.findById(submissionRequestDTO.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + submissionRequestDTO.getUserId()));

        // Problem 조회
        Problem problem = problemRepository.findById(submissionRequestDTO.getProblemId())
            .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + submissionRequestDTO.getProblemId()));

        // Submission 생성 및 저장
        Submission submission = new Submission(
            user,
            problem,
            submissionRequestDTO.getCode(),
            submissionRequestDTO.getLanguage(),
            submissionRequestDTO.getStatus(),
            java.time.LocalDateTime.now()
        );

        submissionRepository.save(submission);
    }
}