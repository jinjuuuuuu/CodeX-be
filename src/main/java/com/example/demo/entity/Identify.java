package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Identify")
@Getter
@Setter
@NoArgsConstructor
public class Identify {

    @Id
    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String accessToken;

    @Column(nullable = false, length = 100)
    private String refreshToken;
}
