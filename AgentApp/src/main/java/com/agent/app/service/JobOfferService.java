package com.agent.app.service;

import com.agent.app.model.JobOffer;
import com.agent.app.repository.JobOfferRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.utility.Validation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;
    private String componentName = "|com.agent.app.service.JobOfferService|";

    //protected final Log logger = LogFactory.getLog(getClass());

    public boolean createJobOffer(Map<String, String> message){
        boolean retVal = false;
        try {
            if(Validation.validateOnlyText(message.get("position"))
                && Validation.validateNonBrackets(message.get("jobDescription"))
                && Validation.validateNonBrackets(message.get("candidateRequirements"))
                && Validation.validateNonBrackets(message.get("dailyActivities")) && Validation.validateTextWithNumbers(message.get("companyName"))) {
                JobOffer jobOffer = new JobOffer();
                jobOffer.setPosition(message.get("position"));
                jobOffer.setJobDescription(message.get("jobDescription"));
                jobOffer.setCompanyName(message.get("companyName"));
                jobOffer.setCandidateRequirements(message.get("candidateRequirements"));
                jobOffer.setDailyActivities(message.get("dailyActivities"));
                jobOffer.setUserEmail(message.get("email"));
                jobOffer.setUserAPIToken("");
                if (Validation.checkIfEmptyJobOffer(jobOffer)) {
                    log.info(LocalDateTime.now().toString() + "|com.agent.app.service.JobOfferService|User " + message.get("email") + " sent invalid data for job offer");
                    return false;
                }
                jobOfferRepository.save(jobOffer);
                retVal = true;
            }else{
                log.info(LocalDateTime.now().toString() + componentName + "User " + message.get("email") + " sent invalid data for job offer");
            }
        }catch(Exception e){
            log.error(LocalDateTime.now().toString() + componentName + e.toString());
            retVal = false;
        }
        return retVal;
    }

    public boolean setUserAPIToken(Map<String, String> message) {
        try{
            JobOffer jobOffer = jobOfferRepository.findById(Long.valueOf(message.get("id"))).orElse(null);
            if(jobOffer == null){
                return false;
            }else{
                if(Validation.validateNonBrackets(message.get("userAPIToken"))) {
                    jobOffer.setUserAPIToken(message.get("userAPIToken"));
                    jobOfferRepository.save(jobOffer);
                    return true;
                }else{
                    log.info(LocalDateTime.now().toString() + componentName + "Invalid API token has been received");
                }
            }
        }catch(Exception e){
            log.error(LocalDateTime.now().toString() + "|com.agent.app.service.JobOfferService|" + e.toString());
            return false;
        }
        return false;
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
