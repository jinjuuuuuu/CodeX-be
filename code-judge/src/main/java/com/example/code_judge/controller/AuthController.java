package com.example.code_judge.controller;

import com.example.code_judge.domain.RefreshToken;
import com.example.code_judge.domain.User;
import com.example.code_judge.repository.RefreshTokenRepository;
import com.example.code_judge.repository.UserRepository;
import com.example.code_judge.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.code_judge.dto.LoginRequest;
import com.example.code_judge.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOptional.get();
        String accessToken = jwtUtil.generateToken(user.getUserId(), 1000 * 60 * 15); // 15 minutes
        String refreshToken = jwtUtil.generateToken(user.getUserId(), 1000 * 60 * 60 * 24 * 7); // 7 days

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUserId(user);
        tokenEntity.setExpiryDate(Instant.now().plusSeconds(60 * 60 * 24 * 7)); // 7 days
        refreshTokenRepository.save(tokenEntity);

        return ResponseEntity.ok(new LoginResponse(
            user.getUserId(), 
            user.getEmail(), 
            user.getUsername(),
            accessToken,
            refreshToken
        ));   
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Refresh Token 삭제
        refreshTokenRepository.deleteByToken(token);

        return ResponseEntity.ok("Logged out successfully");
    }
}