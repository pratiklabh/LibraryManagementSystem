package com.bway.libraryMS.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.libraryMS.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	
	Book findByNameAndAuthor(String name, String author);

}
