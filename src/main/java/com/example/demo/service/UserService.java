package com.example.demo.service;


import com.example.demo.domain.User;
import java.util.List;
import javax.transaction.Transactional;

public interface UserService {
//    void save(User user);
//    void login(String username, String password);
//    User findByUsername(String username);
//    User findByEmail(String email);
//    User findById(long id);

	List<User> getUsers();
	void addUser(User user);
	void deleteUser(Long id);
	void updateUser(Long id, String username, String password, String email, Float balance);
}
