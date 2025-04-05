package com.example.code_judge;

import com.example.code_judge.domain.Problem;
import com.example.code_judge.repository.ProblemRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Component
public class DataInitializer {
    private final ProblemRepository problemRepository;

    public DataInitializer(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @PostConstruct
    public void initData() {
        System.out.println("✅ DataInitializer 실행 시작!");

        try {
            // MySQL 데이터베이스에서 문제 데이터 가져오기
            List<Problem> problems = problemRepository.findAll();

            if (problems.isEmpty()) {
                System.out.println("❌ 데이터베이스에 문제가 없습니다.");
                return;
            }

            // 문제 데이터 출력
            // problems.forEach(problem -> {
            //     System.out.println("문제 ID: " + problem.getProblemId());
            //     System.out.println("제목: " + problem.getTitle());
            //     System.out.println("난이도: " + problem.getDifficulty());
            //     System.out.println("태그: " + problem.getTags());
            //     System.out.println("예제 입력: " + problem.getExampleInput());
            //     System.out.println("예제 출력: " + problem.getExampleOutput());
            //     System.out.println("-----------------------------------");
            // });

            System.out.println("✅ 데이터베이스에서 문제 데이터를 성공적으로 로드했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ 데이터베이스에서 문제 데이터를 로드하는 중 오류 발생", e);
        }
    }
}