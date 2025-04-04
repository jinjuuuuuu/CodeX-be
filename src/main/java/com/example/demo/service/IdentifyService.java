package com.example.demo.service;

import com.example.demo.entity.Identify;
import com.example.demo.repository.IdentifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentifyService {

    private final IdentifyRepository identifyRepository;

    @Autowired
    public IdentifyService(IdentifyRepository identifyRepository) {
        this.identifyRepository = identifyRepository;
    }

    // 토큰 저장 (로그인 시 호출)
    public Identify saveToken(String email, String accessToken, String refreshToken) {
        Identify identify = new Identify();
        identify.setEmail(email);
        identify.setAccessToken(accessToken);
        identify.setRefreshToken(refreshToken);
        return identifyRepository.save(identify);
    }

    // 토큰 조회
    public Optional<Identify> findByEmail(String email) {
        return identifyRepository.findByEmail(email);
    }

    // 로그아웃: 토큰 삭제
    public void deleteByEmail(String email) {
        identifyRepository.deleteById(email);
    }
}
