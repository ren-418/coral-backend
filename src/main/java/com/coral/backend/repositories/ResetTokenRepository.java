package com.coral.backend.repositories;

import com.coral.backend.entities.ResetToken;
import com.coral.backend.entities.Session;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, String> {
    Optional<ResetToken> findResetTokenByUser(User user);

}
