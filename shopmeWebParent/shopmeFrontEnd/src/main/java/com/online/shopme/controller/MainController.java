package com.online.shopme.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }
}
