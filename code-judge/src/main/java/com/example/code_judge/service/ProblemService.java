package com.example.code_judge.service;

import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import com.example.code_judge.exception.ProblemNotFoundException;
import com.example.code_judge.dto.ProblemListDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public List<ProblemListDTO> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTag()
                ))
                .collect(Collectors.toList());
    }

    public ProblemDTO getProblemById(Integer problemId) {
        return problemRepository.findById(problemId)
                .map(problem -> new ProblemDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDescription(),
                        problem.getDifficulty(),
                        problem.getTag(),
                        problem.getExampleInput(),
                        problem.getExampleOutput()
                ))
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found with ID: " + problemId));
    }

    public List<ProblemListDTO> filterProblems(Integer problemId, Integer difficulty, String tag, String title) {
        return problemRepository.findAll().stream()
                .filter(problem -> problemId == null || problem.getProblemId().equals(problemId))
                .filter(problem -> difficulty == null || problem.getDifficulty().equals(difficulty))
                .filter(problem -> tag == null || problem.getTag().equals(tag))
                .filter(problem -> title == null || problem.getTitle().equals(title))
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTag()
                ))
                .collect(Collectors.toList());
    }
}