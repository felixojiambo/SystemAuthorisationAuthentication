package com.zep.AuthorisationandAuthentication.controllers;
import com.zep.AuthorisationandAuthentication.dtos.LoginRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zep.AuthorisationandAuthentication.dtos.LoginResponseDTO;
import com.zep.AuthorisationandAuthentication.dtos.RegistrationDTO;
import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
import com.zep.AuthorisationandAuthentication.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

 @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getEmail(), body.getPassword(), body.getFirstName(), body.getLastName(),body.getPhoneNumber());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

}
