package Nidhi.com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nidhi.com.example.demo.model.User;
import Nidhi.com.example.demo.service.UserService;




@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		userService.registerUser(user);
		return "User Created Successfully";
	}
	
	@GetMapping("/login")
	public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
	    Optional<User> userOptional = userService.findByemail(email);

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        if (user.getPassword().equals(password)) {
	            if ("ADMIN".equals(user.getRole())) {
	                // Redirect to admin dashboard (HTML)
	                return "redirect:/admin"; // Renders admin.html from templates
	            } else {
	                // Redirect to user track list (HTML)
	                return "redirect:/track-list"; // Renders track-list.html from templates
	            }
	        }
	    }

	    // Invalid credentials: Show error on the same login page
	    model.addAttribute("errorMessage", "Invalid email or password");
	    return "redirect:/index"; // Renders login.html from templates
	}
	
	@GetMapping("/list")
	public String display(Model model) {
		model.addAttribute("userDetail",userService.getAllUsers());
		return "user";
	}



	
}
