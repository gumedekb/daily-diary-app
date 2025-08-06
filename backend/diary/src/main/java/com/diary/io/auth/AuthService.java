package com.diary.io.auth;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diary.io.security.JwtProvider;
import com.diary.io.user.User;
import com.diary.io.user.UserService;

@Service
public class AuthService {
	
	private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
	}
    
    public ResponseEntity<?> signup(SignupRequest request) {
        // Check if user exists
        if (userService.userExists(request.getEmail(), request.getUsername())) {
            return ResponseEntity.badRequest().body("Username or email already taken.");
        }

        // Create new user with encoded password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }
    
    public ResponseEntity<?> login(LoginRequest request) throws IOException {
        try {

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );
            
            System.out.println("AuthManager pass " + request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(request.getUsername());
            
            LoginResponse response = new LoginResponse();
			response.setMessage("User authenticated successfully.");
			response.setUsername(request.getUsername());
			response.setToken(token);

			return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password");
        }
    }

}
