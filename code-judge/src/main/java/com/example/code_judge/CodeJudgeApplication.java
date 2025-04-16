package com.example.code_judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodeJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeJudgeApplication.class, args);
    }
}