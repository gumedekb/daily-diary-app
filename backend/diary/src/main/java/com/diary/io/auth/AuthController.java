package com.diary.io.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
        this.authService = authService;
    }
	
	@PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) throws IOException {
	    return authService.login(request);
	}

}
