package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getStudents() {
		return userService.getUsers();
	}

	@PostMapping
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}

	@DeleteMapping(path = "{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

	@PutMapping(path = "{id}")
	public void updateUser(
		@PathVariable("id") Long id,
		@RequestParam(required = false) String username,
		@RequestParam(required = false) String password,
		@RequestParam(required = false) String email,
		@RequestParam(required = false) Float balance) {
		userService.updateUser(id, username, password, email, balance);
	}
}
