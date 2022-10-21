package com.example.demo.service.impl;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.student.Student;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		boolean exists = userRepository.existsById(id);
		if (!exists) {
			throw new IllegalStateException("User with id " + id + " does not exists");
		}
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void updateUser(Long id, String username, String password, String email, Float balance) {

		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalStateException(
				"user with id " + id + " does not exist"
			));

//		if (!user.getPassword().equals(password)) {
//			throw new IllegalStateException(
//				"username does not match password"
//			);
//		}
		if (username != null) {
			user.setUsername(username);
		}

		if (password != null) {
			user.setPassword(password);
		}

		if (email != null) {
			user.setEmail(email);
		}

		if (balance != null) {
			user.setBalance(balance);
		}

	}

}
