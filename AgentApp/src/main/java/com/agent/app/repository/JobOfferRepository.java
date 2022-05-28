package com.agent.app.repository;

import com.agent.app.model.JobOffer;
import com.agent.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    JobOffer findByUserAPIToken(String userAPIToken);
    List<JobOffer> findAllByUserEmail(String userEmail);
}
