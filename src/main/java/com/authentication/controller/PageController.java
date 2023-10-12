package com.authentication.controller;

import com.authentication.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class PageController {
    private RegistrationService registrationService;
    @GetMapping("/user")
    public String getUser(){return "user";}
    @GetMapping("/admin")
    public String getAdmin(){return "admin";}
    @GetMapping
    public String getAll(){return "hello";}

}
