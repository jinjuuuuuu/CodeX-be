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
                .map(problem -> submissionRepository.getAllProblemStatistics().stream()
                        .filter(stat -> stat.getProblemId().equals(problem.getProblemId()))
                        .findFirst()
                        .map(stat -> new ProblemListDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                stat.getTotalSubmitted(),
                                stat.getTotalAccuracy()
                        ))
                        .orElse(new ProblemListDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                0L,
                                "-"
                        )));
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
                .map(problem -> submissionRepository.getAllProblemStatistics().stream()
                        .filter(stat -> stat.getProblemId().equals(problem.getProblemId()))
                        .findFirst()
                        .map(stat -> new ProblemListDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                stat.getTotalSubmitted(),
                                stat.getTotalAccuracy()
                        ))
                        .orElse(new ProblemListDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                0L,
                                "-"
                        )));
    }

    public ProblemDTO getProblemById(Long problemId) {
        return problemRepository.findById(problemId)
                .map(problem -> submissionRepository.getAllProblemStatistics().stream()
                        .filter(stat -> stat.getProblemId().equals(problemId))
                        .findFirst()
                        .map(stat -> new ProblemDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDescription(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                problem.getExampleInput(),
                                problem.getExampleOutput(),
                                stat.getTotalSubmitted(),
                                stat.getTotalAccuracy()
                        ))
                        .orElse(new ProblemDTO(
                                problem.getProblemId(),
                                problem.getTitle(),
                                problem.getDescription(),
                                problem.getDifficulty(),
                                problem.getTags(),
                                problem.getExampleInput(),
                                problem.getExampleOutput(),
                                0L,
                                "-"
                        )))
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found with ID: " + problemId));
    }
}