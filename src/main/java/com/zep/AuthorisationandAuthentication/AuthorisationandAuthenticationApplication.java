package com.zep.AuthorisationandAuthentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
import com.zep.AuthorisationandAuthentication.models.Role;
import com.zep.AuthorisationandAuthentication.repository.RoleRepository;
import com.zep.AuthorisationandAuthentication.repository.UserRepository;

@SpringBootApplication
public class AuthorisationandAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorisationandAuthenticationApplication.class, args);
		System.out.println("Felix");
	}


@Bean
CommandLineRunner run(RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder){
	return  args ->{

		if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
		Role adminRole=roleRepository.save(new Role("ADMIN"));
		roleRepository.save(new Role("USER"));
		Set<Role> roles=new HashSet<>();
		roles.add(adminRole);
		ApplicationUser admin=new ApplicationUser(1,"admin",passwordEncoder.encode("password"),roles);
	userRepository.save(admin);
	};

}}