package com.zep.AuthorisationandAuthentication.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
 @GetMapping("/")
    public String  helloUserController(){
        return "User access level";
    }
}
