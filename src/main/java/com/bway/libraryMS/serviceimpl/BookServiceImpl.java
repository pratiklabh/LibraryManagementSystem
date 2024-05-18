package com.bway.libraryMS.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.libraryMS.model.Book;
import com.bway.libraryMS.repository.BookRepository;
import com.bway.libraryMS.service.BookService;
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepo;
	
	
	
	@Override
	public void addBook(Book book) {
		bookRepo.save(book);
	}

	@Override
	public void deleteBook(int id) {
		bookRepo.deleteById(id);
		
	}

	@Override
	public void updateBook(Book book) {
		bookRepo.save(book);
	}

	@Override
	public Book getBookById(int id) {
		return bookRepo.findById(id).get();
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}

	@Override
	public Book searchBook(String name, String author) {
		return bookRepo.findByNameAndAuthor(name, author);
	}

	
}
