package com.example.demo.repository;

import com.example.demo.entity.Identify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentifyRepository extends JpaRepository<Identify, String> {

    Optional<Identify> findByEmail(String email);
}
