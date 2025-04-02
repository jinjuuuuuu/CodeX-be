package com.example.code_judge.repository;

import com.example.code_judge.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
@Query("SELECT p FROM Problem p WHERE " +
            "(:problemId IS NULL OR p.problemId = :problemId) AND " +
            "(:difficulty IS NULL OR p.difficulty = :difficulty) AND " +
            "(:tag IS NULL OR p.tags LIKE %:tag%) AND " +
            "(:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Problem> filterProblems(@Param("problemId") Long problemId,
                                 @Param("difficulty") Integer difficulty,
                                 @Param("tag") String tag,
                                 @Param("title") String title,
                                 Pageable pageable);
}
