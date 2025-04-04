package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired  // 생성자 주입 (생략 가능)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 이메일로 사용자 조회
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // username으로 사용자 조회
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 사용자 저장
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
