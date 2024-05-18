package com.bway.libraryMS.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.libraryMS.model.AssignedBook;
import com.bway.libraryMS.model.User;
import com.bway.libraryMS.service.AssignedBookService;
import com.bway.libraryMS.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AssignedBookService assignedbookService;
	
	@GetMapping("/userSignup")
	public String getUSerSignup(HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		return "UserSignupForm";
	}
	
	@PostMapping("/userSignup")
	public String postUserSignup(@ModelAttribute User user, Model model) {
		
		
		if(userService.userExists(user.getUsername()) == true) {
			
			model.addAttribute("error", " User Name Already Exits !!!");
			return "UserSignupForm";
		}
		
		
		userService.addUser(user);
		model.addAttribute("message", "User Added Successfully !!!");
		return "UserSignupForm";
	}
	
	@GetMapping("/userList")
	public String getUserList(Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		model.addAttribute("uList", userService.getAllUsers());
		return "UserListForm";
	}
	
	@GetMapping("/user/delete")
	public String delete(@RequestParam int id,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		userService.deleteUser(id);
		return "redirect:/userList";
	}

	@GetMapping("/user/edit")
	public String edit(@RequestParam int id, Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		model.addAttribute("uModel", userService.getUserById(id));
		return "UserEditForm";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute User user) {
		userService.updateUser(user);
		return "redirect:/userList";
	}
	
	//book assigned history
	@GetMapping("/user/bookhistory")
	public String getBooksHistory(@RequestParam int id, Model model,HttpSession session) {
		
		if(session.getAttribute("validAdmin") == null) {
			return "LoginForm";
		}
		
		User user = userService.getUserById(id);
		List<AssignedBook> abook = assignedbookService.getAllAssignedBooks();	
		
		List<AssignedBook> abList = new ArrayList<AssignedBook>();
		
		for(AssignedBook a: abook) {
			if(a.getUsername().equalsIgnoreCase(user.getUsername())) {
				abList.add(a);
			}
			
		}
		model.addAttribute("abList", abList);
		return "UserBookHistory";
	}
	
}
