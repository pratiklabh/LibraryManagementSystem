package com.bway.libraryMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.libraryMS.model.AssignedBook;

public interface AssignedBookRepository extends JpaRepository<AssignedBook, Integer>{
	
	AssignedBook findByBookname(String bookname);
}
