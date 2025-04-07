package com.example.code_judge.service;
import com.example.code_judge.dto.ProblemSubmissionDTO;
import com.example.code_judge.dto.UserSubmissionSummaryDTO;
import com.example.code_judge.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SubmissionRepository submissionRepository;

    public UserSubmissionSummaryDTO getUserSubmissionSummary(Long userId) {
        return submissionRepository.getUserSubmissionSummary(userId);
    }

    public List<ProblemSubmissionDTO> getProblemSubmissions(Long userId) {
        return submissionRepository.getProblemSubmissions(userId);
    }

    public List<UserSubmissionSummaryDTO> getAllUserSubmissionSummaries() {
        return submissionRepository.getAllUserSubmissionSummaries();
    }
}