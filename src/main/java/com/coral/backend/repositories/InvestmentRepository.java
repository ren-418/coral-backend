package com.coral.backend.repositories;

import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Investment;
import com.coral.backend.entities.InvestorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
  Investment findInvestmentByInvestmentId(Long investmentId);

  Optional<Investment> findInvestmentByInvestorAndEnterprise(InvestorUser investor, EnterpriseUser enterprise);

  Optional<List<Investment>> findAllByEnterprise (EnterpriseUser enterprise);
}
