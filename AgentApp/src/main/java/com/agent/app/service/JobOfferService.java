package com.agent.app.service;

import com.agent.app.model.JobOffer;
import com.agent.app.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;

    public boolean createJobOffer(Map<String, String> message){
        try {
            JobOffer jobOffer = new JobOffer();
            jobOffer.setPosition(message.get("position"));
            jobOffer.setJobDescription(message.get("jobDescription"));
            jobOffer.setCompanyName(message.get("companyName"));
            jobOffer.setCandidateRequirements(message.get("candidateRequirements"));
            jobOffer.setDailyActivities(message.get("dailyActivities"));
            jobOffer.setUserAPIToken("");
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

    public Map<String, String> fillMap(Map<String, String> jobOffer, String id) {
        JobOffer offer = jobOfferRepository.findById(Long.valueOf(id)).orElse(null);
        if(offer==null){
            return jobOffer;
        }else{
            jobOffer.put("id", offer.getId().toString());
            jobOffer.put("position", offer.getPosition());
            jobOffer.put("companyName", offer.getCompanyName());
            jobOffer.put("jobDescription", offer.getJobDescription());
            jobOffer.put("dailyActivities", offer.getDailyActivities());
            jobOffer.put("candidateRequirements", offer.getCandidateRequirements());
            jobOffer.put("userAPIToken", offer.getUserAPIToken());
            return jobOffer;
        }
    }

    public JobOffer findOfferById(String id) {
        return jobOfferRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
