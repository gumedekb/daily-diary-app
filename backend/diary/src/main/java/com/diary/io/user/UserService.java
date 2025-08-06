package com.diary.io.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	// Constructor injection preferred
	public UserService(UserRepository userRep) {
		this.userRepository = userRep;
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	
	public User saveUser(User user) {
        return userRepository.save(user);
    }
	
	public boolean userExists(String email, String username) {
        return userRepository.findByEmail(email).isPresent() || userRepository.findByUsername(username).isPresent();
    }

	// ✅ Get user by ID
	public Optional<User> getUserById(Long id) {
	    return userRepository.findById(id);
	}

	// ✅ Update user
	public Optional<User> updateUser(Long id, User userDetails) {
	    return userRepository.findById(id).map(user -> {
	        user.setUsername(userDetails.getUsername());
	        user.setEmail(userDetails.getEmail());
	        user.setPassword(userDetails.getPassword());
	        return userRepository.save(user);
	    });
	}
	


}
