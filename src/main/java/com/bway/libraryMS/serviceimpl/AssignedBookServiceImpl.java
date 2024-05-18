package com.bway.libraryMS.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.libraryMS.model.AssignedBook;
import com.bway.libraryMS.repository.AssignedBookRepository;
import com.bway.libraryMS.service.AssignedBookService;
@Service
public class AssignedBookServiceImpl implements AssignedBookService{

	@Autowired
	private AssignedBookRepository assignedBookRepo;
	
	@Override
	public void addAssignedBook(AssignedBook assignedBook) {
		assignedBookRepo.save(assignedBook);
	}

	@Override
	public void updateAssignedBook(AssignedBook assignedBook) {
		assignedBookRepo.save(assignedBook);
	}

	@Override
	public void deleteAssignedBook(int id) {
		assignedBookRepo.deleteById(id);
	}

	@Override
	public AssignedBook getAssignedBookById(int id) {
		return assignedBookRepo.findById(id).get();
	}

	@Override
	public List<AssignedBook> getAllAssignedBooks() {
		return assignedBookRepo.findAll();
	}

	@Override
	public AssignedBook searchAssignedBook(String bookname) {
		return assignedBookRepo.findByBookname(bookname);
	}

}
