package com.example.code_judge.service;

import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.dto.ProblemListDTO;
import com.example.code_judge.exception.ProblemNotFoundException;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;

    public ProblemService(ProblemRepository problemRepository, SubmissionRepository submissionRepository) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
    }

    public Page<ProblemListDTO> getAllProblemsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("problemId").ascending());
        return problemRepository.findAll(pageable)
                .map(problem -> {
                // 제출 인원 수 계산
                Long totalSubmitted = submissionRepository.countDistinctUsersByProblemId(problem.getProblemId());

                // 정답률 계산
                String totalAccuracy;
                if (totalSubmitted == 0) {
                    totalAccuracy = "-"; // 제출한 인원이 없으면 "-"
                } else {
                    Double accuracy = submissionRepository.getProblemAccuracyByUsers(problem.getProblemId());
                    totalAccuracy = accuracy != null ? String.format("%.1f", accuracy) : "-"; // 소수점 1자리까지 포맷
                }

                // ProblemListDTO 생성
                return new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags(),
                        totalSubmitted,
                        totalAccuracy
                );
            });
    }

    public Page<ProblemListDTO> filterProblemsPaged(Long problemId, Integer difficulty, String tag, String title, int page, int size, String sort) {
        Sort sortBy;
        switch (sort) {
            case "difficulty_asc":
                sortBy = Sort.by(Sort.Order.asc("difficulty"));
                break;
            case "difficulty_desc":
                sortBy = Sort.by(Sort.Order.desc("difficulty"));
                break;
            default:
                sortBy = Sort.by(Sort.Order.asc("problemId")); // 기본값
        }

        Pageable pageable = PageRequest.of(page, size, sortBy);
        return problemRepository.filterProblems(problemId, difficulty, tag, title, pageable)
            .map(problem -> {
                // 제출 인원 수 계산
                Long totalSubmitted = submissionRepository.countDistinctUsersByProblemId(problem.getProblemId());

                // 정답률 계산
                String totalAccuracy;
                if (totalSubmitted == 0) {
                    totalAccuracy = "-"; // 제출한 인원이 없으면 "-"
                } else {
                    Double accuracy = submissionRepository.getProblemAccuracyByUsers(problem.getProblemId());
                    totalAccuracy = accuracy != null ? String.format("%.1f", accuracy) : "-"; // 소수점 1자리까지 포맷
                }

                // ProblemListDTO 생성
                return new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags(),
                        totalSubmitted,
                        totalAccuracy
                );
            });
    }

    public ProblemDTO getProblemById(Long problemId) {
        return problemRepository.findById(problemId)
                .map(problem -> {
                    // 제출 인원 계산
                    Long totalSubmitted = submissionRepository.countDistinctUsersByProblemId(problemId);

                    // 정답률 계산
                    String totalAccuracy;
                    if (totalSubmitted == 0) {
                        totalAccuracy = "-"; // 제출한 인원이 없으면 "-"
                    } else {
                        totalAccuracy = String.valueOf(submissionRepository.getProblemAccuracyByUsers(problemId));
                    }
                    
                    // ProblemDTO 생성
                    return new ProblemDTO(
                            problem.getProblemId(),
                            problem.getTitle(),
                            problem.getDescription(),
                            problem.getDifficulty(),
                            problem.getTags(),
                            problem.getExampleInput(),
                            problem.getExampleOutput(),
                            totalSubmitted,
                            totalAccuracy
                    );
                })
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found with ID: " + problemId));
    }
}