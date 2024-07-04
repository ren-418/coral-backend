package com.coral.backend.repositories;

import com.coral.backend.entities.DatedInvestment;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Investment;
import com.coral.backend.entities.InvestorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatedInvestmentRepository extends JpaRepository<DatedInvestment, Long> {

    Optional<List<DatedInvestment>> findAllByInvestorAndTimeStampBetween(InvestorUser investor, String fromDate, String toDate);

    Optional<List<DatedInvestment>> findAllByEnterpriseAndTimeStampBetween(EnterpriseUser enterprise, String fromDate, String toDate);
}
