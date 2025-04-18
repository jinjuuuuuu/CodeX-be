package com.example.code_judge.domain;

import javax.validation.constraints.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String username;

    @NotNull
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Password is required")
    // @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Submission> submissions;
}