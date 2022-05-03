package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.dto.EducationDto;
import com.vinsguru.grpc.dto.UserDto;
import com.vinsguru.grpc.dto.WorkExperienceDto;

import com.vinsguru.grpc.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class AggregatorController {

    @Autowired
    private AggregatorService aggregatorService;

    @PostMapping("/register")
    public String addUser(@RequestBody Map<String, String> message){
        return aggregatorService.addUser(message);
    }

    @GetMapping("/invalidateUser")
    public String invalidateUser(){
        return aggregatorService.invalidateUser("");
    }

    @PostMapping("/logInUser")
    public String logInUser(@RequestBody Map<String, String> message){
        return aggregatorService.logInUser(message);
    }

    @PostMapping("/personalInfo")
    public String updateUser(@RequestBody Map<String, String> userDto){
       return aggregatorService.updateUser(userDto);
    }

    @PostMapping("/education")
    public String updateEducation(@RequestBody EducationDto educationDto){
        return aggregatorService.updateEducation(educationDto);
    }
    @GetMapping("/user/{email:.+}/")
    public UserDto getUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getUserByEmail(email);
    }
    @GetMapping("/educations/{email:.+}/")
    public List<EducationDto> getEducationsUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getEducationsUserByEmail(email);
    }
    @PostMapping("/workExperiences")
    public String updateWorkExperiences(@RequestBody WorkExperienceDto workExperienceDto){
        return aggregatorService.updateWorkExperiences(workExperienceDto);
    }


    @GetMapping("/getAllUsers")
    public List<UserDto> getUsers(){
        return aggregatorService.getUsers();
    }

    @GetMapping("/searchUsers/{param}")
    public List<UserDto> searchUsers(@PathVariable("param") String param){


       // String url = "http://localhost:8080/searchUsers/queryParameter= {queryParameter}";

       // RestTemplate template = new RestTemplate();

       // HttpHeaders headers = new HttpHeaders();
       // HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        //ResponseEntity<Map> response = template.exchange(, HttpMethod.GET, requestEntity, Map.class, uriVariables);


        //return response.getBody();
        return aggregatorService.searchUsers(param);
    }

}
