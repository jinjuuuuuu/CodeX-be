package com.example.code_judge.controller;
import com.example.code_judge.dto.ProblemSubmissionDTO;
import com.example.code_judge.dto.UserSubmissionSummaryDTO;
import com.example.code_judge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/submission-summary")
    public ResponseEntity<UserSubmissionSummaryDTO> getSubmissionSummary(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserSubmissionSummary(userId));
    }

    @GetMapping("/{userId}/problem-submissions")
    public ResponseEntity<List<ProblemSubmissionDTO>> getProblemSubmissions(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProblemSubmissions(userId));
    }
}