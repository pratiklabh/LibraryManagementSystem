package com.bway.libraryMS.service;

import java.util.List;

import com.bway.libraryMS.model.Book;

public interface BookService {
	
void addBook(Book book);
	
	void deleteBook(int id);

	void updateBook(Book book);
	
	Book getBookById(int id);
	
	List<Book> getAllBooks();
	
	Book searchBook(String name, String author);

}
