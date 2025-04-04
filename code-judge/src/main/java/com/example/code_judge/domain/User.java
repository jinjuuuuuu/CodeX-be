package com.example.code_judge.domain;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @NotNull
    @Column(columnDefinition = "TEXT", length = 100)
    private String email;
    
    @NotNull
    private String username;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Submission> submissions;
}