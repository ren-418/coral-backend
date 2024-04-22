package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserId(int user_id);

    @Query("SELECT u FROM InvestorUser u " +
            "LEFT JOIN u.areas a " +
            "WHERE (:investorType IS NULL OR u.investorType = :investorType) " +
            "AND (:location IS NULL OR u.location IN :location) " +
            "AND (:interests IS NULL OR a IN :interests) " +
            "GROUP BY u " +
            "ORDER BY " +
            "(CASE WHEN :interests IS NULL THEN 0 ELSE " +
            "(SELECT COUNT(a) FROM u.areas a WHERE a IN :interests) END) DESC")
    Optional<List<InvestorUser>> findMatchingInvestors(
            @Param("investorType") Integer investorType,
            @Param("location") List<String> location,
            @Param("interests") List<Area> interests);

}