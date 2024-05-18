package com.bway.libraryMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.libraryMS.model.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Integer>{

	Admin findByEmail(String email);
	
	Admin findByUsernameAndPassword(String username, String password);

}
