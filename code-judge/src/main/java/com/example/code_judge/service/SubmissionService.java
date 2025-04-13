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

    public String submitCode(SubmissionRequestDTO request) {
        // 간단한 로직: 제출된 코드와 언어를 확인하고 성공 메시지 반환
        if (request.getUserId() == null || request.getProblemId() == null || request.getCode() == null || request.getLanguage() == null) {
            throw new IllegalArgumentException("Invalid submission request");
        }

        
        saveSubmission(request);

        // 여기서는 간단히 성공 메시지를 반환
        return "Success";
    }
}