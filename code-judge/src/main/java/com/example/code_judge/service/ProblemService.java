package com.example.code_judge.service;

import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.repository.ProblemRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.code_judge.exception.ProblemNotFoundException;
import com.example.code_judge.dto.ProblemListDTO;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Page<ProblemListDTO> getAllProblemsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("problemId").ascending());
        return problemRepository.findAll(pageable)
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags()
                ));
    }

    public Page<ProblemListDTO> filterProblemsPaged(Long problemId, Integer difficulty, String tag, String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("problemId").ascending());
        return problemRepository.filterProblems(problemId, difficulty, tag, title, pageable)
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags()
                ));
    }

    public ProblemDTO getProblemById(Long problemId) {
        return problemRepository.findById(problemId)
                .map(problem -> new ProblemDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDescription(),
                        problem.getDifficulty(),
                        problem.getTags(),
                        problem.getExampleInput(),
                        problem.getExampleOutput()
                ))
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found with ID: " + problemId));
    }
}