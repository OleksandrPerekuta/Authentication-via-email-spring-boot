package com.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/user")
    public String getUser(){return "user";}
    @GetMapping("/admin")
    public String getAdmin(){return "admin";}
    @GetMapping
    public String getAll(){return "hello";}
}
