package com.bway.libraryMS.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.libraryMS.model.Admin;
import com.bway.libraryMS.repository.AdminRepository;
import com.bway.libraryMS.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepo;
	
	
	@Override
	public void addAdmin(Admin admin) {

		adminRepo.save(admin);
		
	}

	@Override
	public void updateAdmin(Admin admin) {

		adminRepo.save(admin);
		
	}

	@Override
	public Admin searchUsernameAndPassword(String username, String password) {
		return adminRepo.findByUsernameAndPassword(username, password);
	}

	@Override
	public Admin searchEmail(String email) {
		
		return adminRepo.findByEmail(email);
	}

	@Override
	public Admin getAdminById(int id) {
		return adminRepo.findById(id).get();
	}

}
