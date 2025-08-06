package com.diary.io.auth;

public class LoginResponse {
	private String message;
	private String username;
    private String token;

    // Getters and setters
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
	
}
