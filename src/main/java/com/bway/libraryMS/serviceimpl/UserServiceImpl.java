package com.bway.libraryMS.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.libraryMS.model.User;
import com.bway.libraryMS.repository.UserRepository;
import com.bway.libraryMS.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void addUser(User user) {
		userRepo.save(user);
	}

	@Override
	public void deleteUser(int id) {
			userRepo.deleteById(id);
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
	}

	@Override
	public User searchUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public boolean userExists(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public User getUserById(int id) {
		return userRepo.findById(id).get();
	}

}
