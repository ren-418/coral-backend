package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserId(long userId);
    Optional<List<User>> findAllByNameStartingWithIgnoreCase(String prefix);
    Optional<User> findUserByName(String name);

}