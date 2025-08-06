package com.diary.io.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;

	@Autowired
	public UserController(UserService userServ) {
		this.userService = userServ;
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User created = userService.saveUser(user);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
	    return userService.updateUser(id, userDetails)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
	
}
