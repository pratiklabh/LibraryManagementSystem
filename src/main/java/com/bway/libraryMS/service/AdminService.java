package com.bway.libraryMS.service;


import com.bway.libraryMS.model.Admin;

public interface AdminService {
	
	void addAdmin(Admin admin);

	void updateAdmin(Admin admin);
	
	Admin searchUsernameAndPassword(String username, String password);
	
	Admin searchEmail(String email);
	
	Admin getAdminById(int id);
	
}
