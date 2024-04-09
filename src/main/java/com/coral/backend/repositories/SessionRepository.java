package com.coral.backend.repositories;

import com.coral.backend.entities.Session;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    void deleteSessionBySessionToken(String sessionToken);
    Optional<Session> findSessionBySessionToken(String sessionToken);
    Optional<Session> findSessionByUser(User user);
}
