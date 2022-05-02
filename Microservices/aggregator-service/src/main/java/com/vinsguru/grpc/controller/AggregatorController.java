package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.dto.UserDTO;
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

    @PostMapping("/updateUser")
    public void updateUser(){

    }

    @GetMapping("/getAllUsers")
    public List<UserDTO> getUsers(){
        return aggregatorService.getUsers();
    }

    @GetMapping("/searchUsers/{param}")
    public List<UserDTO> searchUsers(@PathVariable("param") String param){


       // String url = "http://localhost:8080/searchUsers/queryParameter= {queryParameter}";

       // RestTemplate template = new RestTemplate();

       // HttpHeaders headers = new HttpHeaders();
       // HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        //ResponseEntity<Map> response = template.exchange(, HttpMethod.GET, requestEntity, Map.class, uriVariables);


        //return response.getBody();
        return aggregatorService.searchUsers(param);
    }

}
