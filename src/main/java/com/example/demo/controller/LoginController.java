package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * login
     * @return
     */
    @RequestMapping(value = {"","/","/login"})
    public String login(){
        return "login";
    }
}
