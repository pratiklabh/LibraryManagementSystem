package com.bway.libraryMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.libraryMS.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	
}
