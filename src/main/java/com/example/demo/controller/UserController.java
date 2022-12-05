package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.Optional;
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
	private final OrderService orderService;

	@Autowired
	public UserController(UserService userService, OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping
	public List<User> getUsers() {
		return userService.getAll();
	}

	@GetMapping(path = "balance/{id}")
	public Float getTotalBalance(@PathVariable("id") Long id) {
		return orderService.getTotalBalanceByUserId(id);
	}

	@GetMapping(path = "{id}")
	public Optional<User> getUser(@PathVariable("id") Long id) {
		return userService.get(id);
	}

	@PostMapping
	public void addUser(@RequestBody User user) {
		userService.add(user);
	}

	@DeleteMapping(path = "{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
	}

	@PutMapping(path = "{id}")
	public void updateUser(
		@PathVariable("id") Long id,
		@RequestParam(required = false) String username,
		@RequestParam(required = false) String password,
		@RequestParam(required = false) String email,
		@RequestParam(required = false) Float balance) {
		userService.update(id, username, password, email, balance);
	}
}
