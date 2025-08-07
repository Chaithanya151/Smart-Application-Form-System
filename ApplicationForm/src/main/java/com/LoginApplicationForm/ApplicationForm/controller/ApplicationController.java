package com.LoginApplicationForm.ApplicationForm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LoginApplicationForm.ApplicationForm.entity.Application;
import com.LoginApplicationForm.ApplicationForm.service.Service;

@Controller
public class ApplicationController {
	 @Autowired
	    private Service service;

	    @GetMapping("/")
	    public String home(Model model) {
//	        model.addAttribute("application", new Application());
	        return "form";
	    }

	    @PostMapping("/app/sing")
	    public String sing(Model model) {
          model.addAttribute("application", new Application()); 
	        return "sign";
	    }
	    
	    
	    @PostMapping("/app/sing/save")
	    public String saveData(@ModelAttribute Application app, Model model) {
	        service.saveData(app);
	        model.addAttribute("application", new Application()); 
	        return "form";
	    }

	    @GetMapping("/app/login")
	    public String showLoginPage() {
	        return "login";
	    }
 
	    
	    @PostMapping("/app/login/home")
	    public String logIn(@RequestParam String userName, @RequestParam String password) {
	        return service.logIn(userName, password);
	    }

	    @PostMapping("/send-otp")
	    public String sendOtp(@RequestParam String email, Model model) {
	        service.generateAndSendOtp(email);
	        model.addAttribute("email", email);
	        model.addAttribute("message", "OTP sent to: " + email);
	        model.addAttribute("application", new Application()); // 👈 add this
	        return "sign";
	    }


	    @PostMapping("/verify-otp")
	    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
	        boolean isValid = service.validateOtp(email, otp);
	        if (isValid) {
	            model.addAttribute("success", "✅ OTP Verified Successfully!");
	        } else {
	            model.addAttribute("error", "❌ Invalid or Expired OTP.");
	        }
	        model.addAttribute("application", new Application()); // 👈 add this
	        model.addAttribute("email", email);
	        return "sign";
	    }

	   
}
