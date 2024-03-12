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
	public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Check if the ADMIN role already exists
			if (roleRepository.findByAuthority("ADMIN").isPresent()) {
				System.out.println("ADMIN role already exists. Skipping role creation.");
				return;
			}

			// Create and save the ADMIN role
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			System.out.println("ADMIN role created.");

			// Create and save the USER role
			Role userRole = roleRepository.save(new Role("USER"));
			System.out.println("USER role created.");

			// Create a set of roles for the admin user
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			// Create and save the admin user
			ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncoder.encode("password"), roles);
			userRepository.save(admin);
			System.out.println("Admin user created.");
		};
	}


}