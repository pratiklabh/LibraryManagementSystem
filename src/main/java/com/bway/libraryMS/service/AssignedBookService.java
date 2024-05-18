package com.bway.libraryMS.service;

import java.util.List;

import com.bway.libraryMS.model.AssignedBook;

public interface AssignedBookService {
	
	void addAssignedBook(AssignedBook assignedBook);
	
	void updateAssignedBook(AssignedBook assignedBook);
	
	void deleteAssignedBook(int id);
	
	AssignedBook getAssignedBookById(int id);

	List<AssignedBook> getAllAssignedBooks();
	
	AssignedBook searchAssignedBook(String bookname);
}
