package com.example.code_judge.repository;

import com.example.code_judge.domain.Submission;
import com.example.code_judge.dto.ProblemSubmissionDTO;
import com.example.code_judge.dto.UserSubmissionSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("SELECT new com.example.code_judge.dto.UserSubmissionSummaryDTO(" +
           "s.user.userId, COUNT(s), " +
           "SUM(CASE WHEN s.status = 'PASS' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN s.status = 'FAIL' THEN 1 ELSE 0 END), " +
           "ROUND(SUM(CASE WHEN s.status = 'PASS' THEN 1 ELSE 0 END) * 100.0 / COUNT(s), 2)) " +
           "FROM Submission s WHERE s.user.userId = :userId GROUP BY s.user.userId")
    UserSubmissionSummaryDTO getUserSubmissionSummary(@Param("userId") Long userId);

    @Query("SELECT new com.example.code_judge.dto.ProblemSubmissionDTO(" +
           "s.problem.problemId, " +
           "MAX(CASE WHEN s.status = 'PASS' THEN 'PASS' ELSE 'FAIL' END), " +
           "COUNT(s)) " +
           "FROM Submission s WHERE s.user.userId = :userId GROUP BY s.problem.problemId")
    List<ProblemSubmissionDTO> getProblemSubmissions(@Param("userId") Long userId);
}