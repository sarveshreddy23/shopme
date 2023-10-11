package com.shopme.admin.controller;

import com.shopme.admin.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    RoleService service;
    @GetMapping("")
    @PostMapping("/")
    public String viewHomePage(){
        System.out.println(":: Navigating to Home Page");
        return "index";
    }

    @GetMapping("/login")
    public String dologin(){
        return "login";
    }

    @GetMapping("/addRole")
    public String addRole(){
            service.addrole();
        return "index";
    }
}
