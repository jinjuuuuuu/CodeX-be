package com.example.code_judge.service;

import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.dto.ProblemListDTO;
import com.example.code_judge.exception.ProblemNotFoundException;
import com.example.code_judge.repository.ProblemRepository;
import com.example.code_judge.repository.SubmissionRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @PostConstruct
    public void preloadCache() {
        getAllProblemsPaged(0, 10);
    }

    @Cacheable("allProblemsPaged")
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

    @CacheEvict(value = "allProblemsPaged", allEntries = true) // 캐시 무효화 추가
    public void updateProblem(Long problemId, ProblemDTO updatedProblem) {
        problemRepository.findById(problemId).ifPresentOrElse(problem -> {
            problem.setTitle(updatedProblem.getTitle());
            problem.setDescription(updatedProblem.getDescription());
            problem.setDifficulty(updatedProblem.getDifficulty());
            problem.setTags(updatedProblem.getTags());
            problemRepository.save(problem); // 문제 업데이트
        }, () -> {
            throw new ProblemNotFoundException("Problem not found with ID: " + problemId);
        });
    }

    @CacheEvict(value = "allProblemsPaged", allEntries = true) // 캐시 무효화 추가
    public void deleteProblem(Long problemId) {
        if (problemRepository.existsById(problemId)) {
            problemRepository.deleteById(problemId); // 문제 삭제
        } else {
            throw new ProblemNotFoundException("Problem not found with ID: " + problemId);
        }
    }

    @Cacheable("filteredProblemsPaged")
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

    @Cacheable("problemById")
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