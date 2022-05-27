package com.agent.app.controllers;

import com.agent.app.model.JobOffer;
import com.agent.app.security.TokenUtils;
import com.agent.app.service.JobOfferService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class JobOfferController {
    @Autowired
    JobOfferService jobOfferService;

    @Autowired
    TokenUtils tokenUtils;

    @PostMapping("/createJobOffer")
    public boolean createJobOffer(@RequestBody Map<String, String> message){
        return jobOfferService.createJobOffer(message);
    }

    @PostMapping("/sendJobOfferRequest")
    public boolean sendJobOfferRequest(@RequestHeader("Authentication")HttpHeaders header, @RequestBody Map<String, String> message){
        boolean retVal = jobOfferService.setUserAPIToken(message);
        final RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://localhost:8080/api/createJobOffer";
       // final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
       // String email = tokenUtils.getUsernameFromToken(value);
      //  String plainCreds = email+":"+"Dajjedangriz*7";
      //  byte[] plainCredsBytes = plainCreds.getBytes();
      //  byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
      //  String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
       // headers.add("Authorization", "Basic" + base64Creds);
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
            jobOffer.put("userAPItoken", message.get("userAPIToken"));
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
    public List<JobOffer> getAllJobOffers(){
        return jobOfferService.getAllJobOffers();
    }
}
