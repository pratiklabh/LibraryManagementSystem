package com.bway.libraryMS.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.libraryMS.model.Admin;
import com.bway.libraryMS.service.AdminService;
import com.bway.libraryMS.utils.MailUtils;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MailUtils mailutil;
	
	@GetMapping({ "/", "/login" })
	public String getLogin() {
		
		return "Loginform";
	}
	
	@PostMapping({ "/", "/login" })
	public String postLogin(@ModelAttribute Admin admin, Model model, HttpSession session) {
		
		Admin adm = adminService.searchUsernameAndPassword(admin.getUsername(), admin.getPassword());
		
		if(adm != null) {
			
			session.setAttribute("validAdmin", adm);
			
			session.setAttribute("id", adm.getId());
			session.setAttribute("name", adm.getName());
	        session.setAttribute("email", adm.getEmail());
	        session.setAttribute("username", adm.getUsername());
	       
			session.setMaxInactiveInterval(120);
			return "AdminHome";
			
		}
		
		model.addAttribute("error", "User Not Found !!!");
		return "LoginForm";
	}
	
	@GetMapping("/signup")
	public String getSignup() {
		
		return "SignupForm";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute Admin admin, Model model) {
		
		adminService.addAdmin(admin);
		
		model.addAttribute("success", "Signup Successful");
		return "LoginForm";
	}
	
	

	@GetMapping("/adminProfile")
	public String getAdminProfile(HttpSession session, Model model) {
		
		if(session.getAttribute("validAdmin")==null) {
			return "LoginForm";
		}
		
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email");
		String username = (String) session.getAttribute("username");
		
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("username", username);
		
		return "AdminProfileForm";
	}
	
	//to change password
	@GetMapping("/changePassword")
	public String changePassword() {
		
		return "ChangePasswordForm";
	}
	
	@PostMapping("/changePassword")
	public String postChangePassword(@RequestParam String password, String newpassword, String confirmnewpassword, HttpSession session, Model model) {
	    
	    int id = (int) session.getAttribute("id");
	    
	    Admin ad = adminService.getAdminById(id);
	    
	    if(!password.equals(ad.getPassword())) {
	        model.addAttribute("error", "You entered incorrect password !!!");
	        return "ChangePasswordForm";
	    }
	    
	    if(!newpassword.equals(confirmnewpassword)) {
	        model.addAttribute("error", "New Passwords Have To Be Same !!!");
	        return "ChangePasswordForm";
	    }
	    
	    if(password.equals(newpassword)) {
	        model.addAttribute("error", "New Password Cannot be Old Password !!!");
	        return "ChangePasswordForm";
	    }
	    
	    ad.setPassword(newpassword);
	    adminService.updateAdmin(ad);

	    model.addAttribute("message", "Password Changed Successfully !!!");
	    return "ChangePasswordForm";
	}

	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "LoginForm";
	}
	

    @GetMapping("/forgetPassword")
    public String getForgetPassword(HttpSession session) {
    	
    	if(session.getAttribute("validAdmin")==null) {
			return "LoginForm";
		}
    	
        return "ForgetPasswordForm";
    }

    @PostMapping("/forgetPassword")
    public String postForgetPassword(@RequestParam String toEmail, Model model, HttpSession session) {
        Admin adm = adminService.searchEmail(toEmail);

        if (adm == null) {
            model.addAttribute("error", "User with that email doesn't exist !!!");
            return "ForgetPasswordForm";
        }

        // Generate a 6-digit PIN
        Random random = new Random();
        int vCode = random.nextInt(900000) + 100000;

        // Store verification code in the session
        session.setAttribute("verificationCode", vCode);
        session.setAttribute("userEmail", toEmail);
        session.setAttribute("sessionTime", System.currentTimeMillis());

        //send mail
        mailutil.sendEmail(toEmail, "Your Verification Code is: " + vCode);

        model.addAttribute("message", "Verification code sent successfully. Check your email.");
        return "VerificationForm";
    }

    @GetMapping("/verification")
    public String getVerification(HttpSession session) {
    	
    	if(session.getAttribute("validAdmin")==null) {
			return "LoginForm";
		}
    	
        return "VerificationForm";
    }

    @PostMapping("/verification")
    public String postVerification(@RequestParam int userCode, HttpSession session, Model model) {
        Integer sessionCode = (Integer) session.getAttribute("verificationCode");

        if (sessionCode != null && sessionCode.equals(userCode)) {
            model.addAttribute("message", "Verification successful. You can now reset your password.");
            return "PasswordResetForm";
        } else {
            model.addAttribute("error", "Invalid verification code!");
            return "VerificationForm";
        }
    }
    
    @GetMapping("/resetPassword")
    public String getResetPassword(HttpSession session) {
    	
    	if(session.getAttribute("validAdmin")==null) {
			return "LoginForm";
		}
    	
    	return "PasswordResetForm";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String confirmNewPassword, HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail != null) {
            Admin admin = adminService.searchEmail(userEmail);
            if (admin != null) {
                if (newPassword.equals(confirmNewPassword)) {
                    admin.setPassword(newPassword); 
                    adminService.updateAdmin(admin); 
                    model.addAttribute("message", "Password reset successfully.");
                } else {
                    model.addAttribute("error", "Passwords do not match!");
                    return "PasswordResetForm";
                }
            }
        }

        session.invalidate(); // Clear the session attributes
        return "redirect:/login"; // Redirect to the login page
    }
	
}
