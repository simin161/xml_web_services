package com.agent.app.service;

import com.agent.app.model.JobOffer;
import com.agent.app.repository.JobOfferRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.utility.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean createJobOffer(Map<String, String> message){
        try {
            JobOffer jobOffer = new JobOffer();
            jobOffer.setPosition(message.get("position"));
            jobOffer.setJobDescription(message.get("jobDescription"));
            jobOffer.setCompanyName(message.get("companyName"));
            jobOffer.setCandidateRequirements(message.get("candidateRequirements"));
            jobOffer.setDailyActivities(message.get("dailyActivities"));
            jobOffer.setUserEmail(message.get("email"));
            jobOffer.setUserAPIToken("");
            if(Validation.checkIfEmptyJobOffer(jobOffer))
                return false;
            jobOfferRepository.save(jobOffer);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean setUserAPIToken(Map<String, String> message) {
        try{
            JobOffer jobOffer = jobOfferRepository.findById(Long.valueOf(message.get("id"))).orElse(null);
            if(jobOffer == null){
                return false;
            }else{
                jobOffer.setUserAPIToken(message.get("userAPIToken"));
                jobOfferRepository.save(jobOffer);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public JobOffer findOfferById(String id) {
        return jobOfferRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public List<JobOffer> getJobOffersForUser(String email) {
        return jobOfferRepository.findAllByUserEmail(email);
    }
}
