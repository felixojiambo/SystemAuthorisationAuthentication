package com.zep.AuthorisationandAuthentication.controllers;

import com.zep.AuthorisationandAuthentication.dtos.LoginRequestDTO;
import com.zep.AuthorisationandAuthentication.dtos.LoginResponseDTO;
import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
import com.zep.AuthorisationandAuthentication.repository.UserRepository;
import com.zep.AuthorisationandAuthentication.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    @GetMapping("/")
    public String helloAdminController(){
        return "Admin Access";
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginAdmin(@RequestBody LoginRequestDTO request) {
        Optional<ApplicationUser> adminOptional = userRepository.findByEmail(request.getEmail());
        if (adminOptional.isPresent()) {
            ApplicationUser admin = adminOptional.get();
            if (passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                // Admin credentials are correct, proceed with login
                // Create an Authentication object for the admin
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        admin.getEmail(),
                        admin.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
                // Generate JWT for the admin
                String jwt = tokenService.generateJwt(auth);
                LoginResponseDTO response = new LoginResponseDTO(admin, jwt);
                return ResponseEntity.ok(response);
            }
        }
        // If the Optional is empty or the password does not match, return invalid credentials
        LoginResponseDTO response = new LoginResponseDTO(null, null);
        return ResponseEntity.ok(response);
    }

}
