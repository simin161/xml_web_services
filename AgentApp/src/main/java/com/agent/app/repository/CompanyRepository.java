package com.agent.app.repository;

import com.agent.app.model.Company;
import com.agent.app.model.JobOffer;
import com.agent.app.model.Status;
import com.agent.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository  extends JpaRepository<Company, Long> {
    List<Company> findAllByStatus(Status status);
    List<Company> findAllByOwner(User user);
}
