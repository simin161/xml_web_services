package com.agent.app.controllers;

import com.agent.app.model.JobOffer;
import com.agent.app.security.TokenUtils;
import com.agent.app.service.JobOfferService;
import com.agent.app.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class JobOfferController {
    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/getJobOffersForUser")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public List<JobOffer> getJobOffersForUser(@RequestHeader("Authentication")HttpHeaders header){
        return jobOfferService.getJobOffersForUser(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)));
    }

    @PostMapping("/createJobOffer")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public boolean createJobOffer(@RequestHeader("Authentication")HttpHeaders header, @RequestBody Map<String, String> message){
        message.put("email", tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)));
        return jobOfferService.createJobOffer(message);
    }

    @PostMapping("/sendJobOfferRequest")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public boolean sendJobOfferRequest(@RequestHeader("Authentication")HttpHeaders header, @RequestBody Map<String, String> message){
        boolean retVal = jobOfferService.setUserAPIToken(message);
        final RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://localhost:8080/api/createJobOffer";
         HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> jobOffer = new HashMap<>();
        JobOffer offer = jobOfferService.findOfferById(message.get("id"));
        if(offer==null || message.get("userAPIToken")==null){
            jobOffer.put("neam samana", "e jebiga");
            return false;
        }else{
            jobOffer.put("id", offer.getId().toString());
            jobOffer.put("position", offer.getPosition());
            jobOffer.put("companyName", offer.getCompanyName());
            jobOffer.put("jobDescription", offer.getJobDescription());
            jobOffer.put("dailyActivities", offer.getDailyActivities());
            jobOffer.put("candidateRequirements", offer.getCandidateRequirements());
            jobOffer.put("userAPItoken", userService.getUserApiToken(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION))));
        }
        try{
            HttpEntity<Map<String, Object>> entity= new HttpEntity<Map<String, Object>>(jobOffer, headers);
            boolean response = Boolean.TRUE.equals(restTemplate.postForObject(uri, entity, boolean.class));
        }catch(Exception e){
            retVal = false;
            e.printStackTrace();
        }
        return retVal;
    }

    @GetMapping("/getAllJobOffers")
    @PreAuthorize("hasRole('ROLE_REG_CUSTOMER')")
    public List<JobOffer> getAllJobOffers(){ return jobOfferService.getAllJobOffers();}
}
