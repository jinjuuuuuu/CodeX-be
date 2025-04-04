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

// import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    // @GetMapping
    // public List<ProblemListDTO> getAllProblemsPaged(
    //     @RequestParam(defaultValue = "0") int page,
    //     @RequestParam(defaultValue = "10") int size) {
    //     return problemService.getAllProblemsPaged(page, size).getContent();    
    // }

    @GetMapping
    public Page<ProblemListDTO> filterProblemsPaged(
        @RequestParam(value = "problemId", required = false) Long problemId,
        @RequestParam(value = "difficulty", required = false) Integer difficulty,
        @RequestParam(value = "tags", required = false) String tags,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortby", required = false, defaultValue = "null") String sortby) {
        return problemService.filterProblemsPaged(problemId, difficulty, tags, title, page, size, sortby);
    }

    @GetMapping("/{problemId}")
    public ProblemDTO getProblemById(@PathVariable Long problemId) {
        return problemService.getProblemById(problemId);
    }
}