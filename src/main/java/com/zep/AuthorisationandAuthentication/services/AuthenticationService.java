package com.zep.AuthorisationandAuthentication.services;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zep.AuthorisationandAuthentication.dtos.LoginResponseDTO;
import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
import com.zep.AuthorisationandAuthentication.models.Role;
import com.zep.AuthorisationandAuthentication.repository.RoleRepository;
import com.zep.AuthorisationandAuthentication.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
@Slf4j
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ApplicationUser registerUser(String email, String password, String firstName, String lastName, String phoneNumber){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);


        return userRepository.save(new ApplicationUser(0, email, encodedPassword, authorities, firstName, lastName, phoneNumber));
    }


    public LoginResponseDTO loginUser(String email, String password) {
        try {
            // Authenticate the user
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Generate a JWT token for the authenticated user
            String token = tokenService.generateJwt(auth);

            // Retrieve the user details from the database
            ApplicationUser user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Return a LoginResponseDTO that includes the user details and the token
            return new LoginResponseDTO(user, token);

        } catch (AuthenticationException e) {
            log.error("Error {}", e);
            return new LoginResponseDTO(null, "");
        }
    }

}


