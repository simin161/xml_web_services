package com.agent.app.repository;

import com.agent.app.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    JobOffer findByUserAPIToken(String userAPIToken);
}
