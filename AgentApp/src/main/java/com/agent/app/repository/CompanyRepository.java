package com.agent.app.repository;

import com.agent.app.model.Company;
import com.agent.app.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<Company, Long> {
}
