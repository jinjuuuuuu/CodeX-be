package com.example.demo.controller;

import com.example.demo.entity.Identify;
import com.example.demo.service.IdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class IdentifyController {

    private final IdentifyService identifyService;

    @Autowired
    public IdentifyController(IdentifyService identifyService) {
        this.identifyService = identifyService;
    }

    // 로그인: 토큰 저장
    @PostMapping("/login")
    public ResponseEntity<Identify> login(@RequestBody Identify request) {
        Identify saved = identifyService.saveToken(
                request.getEmail(),
                request.getAccessToken(),
                request.getRefreshToken()
        );
        return ResponseEntity.ok(saved);
    }

    // 로그아웃: 토큰 삭제
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        identifyService.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    // 이메일로 토큰 조회
    @GetMapping("/token/{email}")
    public ResponseEntity<Identify> getToken(@PathVariable String email) {
        Optional<Identify> result = identifyService.findByEmail(email);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
