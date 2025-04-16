package com.example.code_judge.controller;

import com.example.code_judge.dto.ProblemSubmissionDTO;
import com.example.code_judge.dto.UserSubmissionSummaryDTO;
import com.example.code_judge.service.UserService;
import com.example.code_judge.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<UserSubmissionSummaryDTO>> getAllUserSubmissionSummaries() {
        return ResponseEntity.ok(userService.getAllUserSubmissionSummaries());
    }

    @GetMapping("/{userId}/submission-summary")
    public ResponseEntity<UserSubmissionSummaryDTO> getSubmissionSummary(@PathVariable Long userId, HttpServletRequest request) {
        validateUserAccess(userId, request);
        return ResponseEntity.ok(userService.getUserSubmissionSummary(userId));
    }

    @GetMapping("/{userId}/problem-submissions")
    public ResponseEntity<List<ProblemSubmissionDTO>> getProblemSubmissions(@PathVariable Long userId, HttpServletRequest request) {
        validateUserAccess(userId, request);
        return ResponseEntity.ok(userService.getProblemSubmissions(userId));
    }

    // 사용자 접근 검증 메서드
    private void validateUserAccess(Long userId, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Unauthorized: Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        Long authenticatedUserId = jwtUtil.extractUserId(token);

        if (!authenticatedUserId.equals(userId)) {
            throw new IllegalArgumentException("Forbidden: You can only access your own data.");
        }
    }
}