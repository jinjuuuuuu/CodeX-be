package com.example.code_judge.controller;

import com.example.code_judge.dto.ProblemDTO;
import com.example.code_judge.dto.ProblemListDTO;
import com.example.code_judge.service.ProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping
    public Page<ProblemListDTO> filterProblemsPaged(
        @RequestParam(value = "problemId", required = false) Long problemId,
        @RequestParam(value = "difficulty", required = false) Integer difficulty,
        @RequestParam(value = "tag", required = false) String tag,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "problemId") String sort){
        return problemService.filterProblemsPaged(problemId, difficulty, tag, title, page, size, sort);
    }

    @GetMapping("/{problemId}")
    public ProblemDTO getProblemById(@PathVariable Long problemId) {
        return problemService.getProblemById(problemId);
    }
}