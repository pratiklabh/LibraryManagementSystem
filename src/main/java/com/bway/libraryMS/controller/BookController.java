package com.bway.libraryMS.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.libraryMS.model.AssignedBook;
import com.bway.libraryMS.model.Book;
import com.bway.libraryMS.service.AssignedBookService;
import com.bway.libraryMS.service.BookService;
import com.bway.libraryMS.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AssignedBookService assignedBookService;
	
	@GetMapping("/addBook")
	public String getAddBook(HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		return "BookSignupForm";
	}
	
	//to add a new book
	@PostMapping("/addBook")
	public String postAddBook(@ModelAttribute Book book, Model model) {
		
		Book bookExists = bookService.searchBook(book.getName(), book.getAuthor());
		
		if( bookExists!= null) {
			
			model.addAttribute("error", " Book Already Exists !!!");
			return "BookSignupForm";
		}
		
		
		bookService.addBook(book);
		model.addAttribute("message", "New Book Added Successfully !!!");
		
		return "BookSignupForm";
	}
	
	//to get book list
	@GetMapping("/bookList")
	public String getBookList(Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		model.addAttribute("bList", bookService.getAllBooks());
		return "BookListForm";
	}
	
	//to delete a book
	@GetMapping("/book/delete")
	public String delete(@RequestParam int id,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		bookService.deleteBook(id);
		return "redirect:/bookList";
	}
	
	
	//to edit book details
	@GetMapping("/book/edit")
	public String edit(@RequestParam int id, Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "AdminLoginForm";
		}
		
		model.addAttribute("bModel", bookService.getBookById(id));
		return "BookEditForm";
	}
	
	//to update book
	@PostMapping("/book/update")
	public String update(@ModelAttribute Book book) {
		bookService.updateBook(book);
		return "redirect:/bookList";
	}
	
	//to assign a book
	@GetMapping("/assignBook")
	public String getAssignBook(Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		model.addAttribute("uList", userService.getAllUsers());
		model.addAttribute("bList", bookService.getAllBooks());
		
		return "AssignBookForm";
	}
	
	@PostMapping("/assignBook")
	public String postAssignedBook(@ModelAttribute AssignedBook assignedBook, Model model) {
		
		AssignedBook assignedBookExists = assignedBookService.searchAssignedBook(assignedBook.getBookname());
		
		if(assignedBookExists!=null) {
			
			model.addAttribute("error", "The book has been issued to someone else already !!!");
			return "AssignBookForm";
			
		}
		assignedBook.setReturndate("Not Returned Yet");
		assignedBookService.addAssignedBook(assignedBook);
		model.addAttribute("message", "Book Assigned Successfully !!!");

		return "AssignBookForm";
	}
	
	//to return a book	
	@GetMapping("/returnBook")
	public String getReturnBook(Model model,HttpSession session) {

		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		List<AssignedBook> aList = assignedBookService.getAllAssignedBooks();
	    model.addAttribute("abList", aList);
	    
	    //  set to store unique usernames, duplicate name dropdown ma dekhauxa so
	    Set<String> uniqueUsernames = new HashSet<>();

	    // unique username ko set
	    for (AssignedBook assignedBook : aList) {
	        uniqueUsernames.add(assignedBook.getUsername());
	    }

	    model.addAttribute("uniqueUsernames", uniqueUsernames);
	    
	    return "ReturnBookForm";
	}

	
	@PostMapping("/returnBook")
	public String postReturnBook(@ModelAttribute AssignedBook assignedBook, Model model) {
		
		AssignedBook abook = assignedBookService.searchAssignedBook(assignedBook.getBookname());
		
		if(abook.getStatus().equalsIgnoreCase("Returned")) {
			
			model.addAttribute("error", "Book Has been Returned Already !!!");
			return "ReturnBookForm";

		}
		
		if(abook.getUsername().equals(assignedBook.getUsername()) && abook.getBookname().equals(assignedBook.getBookname())) {
			
			abook.setStatus("Returned");
			abook.setReturndate(assignedBook.getReturndate());
			assignedBookService.updateAssignedBook(abook);
			
			model.addAttribute("message", "Book Returned Successfully");
			return "ReturnBookForm";
		}

		model.addAttribute("message", "Book Returned Failed");
		return "ReturnBookForm";
	}
	
	
	//view Assigned book History
	
	@GetMapping("/assignedHistory")
	public String getAssignedBookHistory(Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		model.addAttribute("abList", assignedBookService.getAllAssignedBooks());
		return "AssignedHistory";
	}
	
	//to delete
	@GetMapping("/assignedbook/delete")
	public String deleteAssignedBook(@RequestParam int id,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		assignedBookService.deleteAssignedBook(id);
		return "redirect:/assignedHistory";
	}

}
