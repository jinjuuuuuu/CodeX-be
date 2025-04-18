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
import com.example.code_judge.evaluator.CodeExecutor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;

    public Submission saveSubmission(SubmissionRequestDTO submissionRequestDTO) {
        User user = userRepository.findById(submissionRequestDTO.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + submissionRequestDTO.getUserId()));
    
        Problem problem = problemRepository.findById(submissionRequestDTO.getProblemId())
            .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + submissionRequestDTO.getProblemId()));
    
        CodeExecutor codeExecutor = new CodeExecutor();
        String executionResult = codeExecutor.execute(submissionRequestDTO.getCode(), problem.getExampleInput(), submissionRequestDTO.getLanguage());
    
        boolean isPassed = executionResult.trim().equals(problem.getExampleOutput().trim());
        String status = isPassed ? "pass" : "fail";
    
        Submission submission = new Submission(
            user,
            problem,
            submissionRequestDTO.getCode(),
            submissionRequestDTO.getLanguage(),
            status,
            java.time.LocalDateTime.now()
        );
    
        System.out.println("Saving submission with status: " + status);
        submission.setActualOutput(executionResult);
        submissionRepository.save(submission);
        return submission;
    }

    public String submitCode(SubmissionRequestDTO request) {
        // 간단한 로직: 제출된 코드와 언어를 확인하고 성공 메시지 반환
        if (request.getUserId() == null || request.getProblemId() == null || request.getCode() == null || request.getLanguage() == null) {
            throw new IllegalArgumentException("Invalid submission request");
        }

        Submission submission = saveSubmission(request);

        // 상태 반환
        return submission.getStatus();
    }
}