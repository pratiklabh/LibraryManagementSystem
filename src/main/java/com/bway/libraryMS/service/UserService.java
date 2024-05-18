package com.bway.libraryMS.service;

import java.util.List;

import com.bway.libraryMS.model.User;

public interface UserService {
	
	void addUser(User user);
	
	void deleteUser(int id);

	void updateUser(User user);
	
	User searchUser(String username);
	
	User getUserById(int id);

	List<User> getAllUsers();
	
	boolean userExists(String username);

}
