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

//     public Page<ProblemListDTO> getAllProblemsPaged(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size, Sort.by("problemId").ascending());
//         return problemRepository.findAll(pageable)
//                 .map(problem -> new ProblemListDTO(
//                         problem.getProblemId(),
//                         problem.getTitle(),
//                         problem.getDifficulty(),
//                         problem.getTags()
//                 ));
//     }

public Page<ProblemListDTO> filterProblemsPaged(Long problemId, Integer difficulty, String tags, String title, int page, int size, String sortby) {
        // 동적으로 정렬 기준 설정
        Sort sort = getSortByParameter(sortby);
    
        Pageable pageable = PageRequest.of(page, size, sort);
        return problemRepository.filterProblems(problemId, difficulty, tags, title, pageable)
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags()
                ));
    }
    
    // 정렬 기준을 처리하는 메서드
    private Sort getSortByParameter(String sortby) {
        if (sortby == null || sortby.isEmpty()) {
            return Sort.by("problemId").ascending(); // 기본 정렬 기준
        }
    
        switch (sortby.toLowerCase()) {
            case "difficulty_asc":
                return Sort.by("difficulty").ascending();
            case "difficulty_desc":
                return Sort.by("difficulty").descending();
            case "latest":
                return Sort.by("createdAt").descending(); // 최신순 정렬 (createdAt 필드 기준)
            default:
                return Sort.by("problemId").ascending(); // 기본 정렬 기준
        }
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

    public Page<ProblemListDTO> getPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("problemId").ascending());
        return problemRepository.findAll(pageable)
                .map(problem -> new ProblemListDTO(
                        problem.getProblemId(),
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getTags()
                ));
    }
}