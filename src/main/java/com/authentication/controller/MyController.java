package com.authentication.controller;

import com.authentication.model.User;
import com.authentication.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MyController {
    private RegistrationService registrationService;
    @GetMapping("/user")
    public String getUser(){return "user";}
    @GetMapping("/admin")
    public String getAdmin(){return "admin";}
    @GetMapping
    public String getAll(){return "hello";}
    @GetMapping("/login")
    public String customLogin() {return "login";}
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        try {
            UserDetails userDetails = registrationService.registerUser(user);

            // Manually authenticate the user
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    user.getPassword(),
                    userDetails.getAuthorities()
            );

            httpSession.setAttribute("currentUserDetails", userDetails);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            model.addAttribute("registrationSuccess", true);
            return  "redirect:/user"; // Redirect to a user-specific page after registration
        } catch (Exception e) {
            model.addAttribute("registrationError", e.getMessage());
            return "registration";
        }
    }
}
