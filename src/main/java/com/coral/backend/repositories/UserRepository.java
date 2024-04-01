/*package com.coral.backend.repositories;

import com.coral.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findUserByEmail(String email);
    Optional<Users> findUserByUsername(String username);
    Optional<Users> findUserByFirstNameAndLastName(String firstName, String lastName);
}*/