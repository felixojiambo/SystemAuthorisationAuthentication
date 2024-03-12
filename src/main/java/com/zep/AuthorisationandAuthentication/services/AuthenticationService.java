package com.zep.AuthorisationandAuthentication.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zep.AuthorisationandAuthentication.dtos.LoginResponseDTO;
import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
import com.zep.AuthorisationandAuthentication.models.Role;
import com.zep.AuthorisationandAuthentication.repository.RoleRepository;
import com.zep.AuthorisationandAuthentication.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
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


    public LoginResponseDTO loginUser(String email, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}
