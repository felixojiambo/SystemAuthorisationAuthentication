package com.zep.AuthorisationandAuthentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zep.AuthorisationandAuthentication.models.ApplicationUser;
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Integer> {

    Optional<ApplicationUser> findByEmail(String email);
}
