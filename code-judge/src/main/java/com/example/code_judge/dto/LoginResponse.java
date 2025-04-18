package com.example.code_judge.dto;

public class LoginResponse {
    private Long userId;
    private String email;
    private String username;
    private String accessToken;
    private String refreshToken;

    public LoginResponse() {
    }

    public LoginResponse(Long userId, String email, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
