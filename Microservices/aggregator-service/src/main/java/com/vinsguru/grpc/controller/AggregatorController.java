package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AggregatorController {

    @Autowired
    private AggregatorService aggregatorService;

    @PostMapping("/register")
    public void addUser(@RequestBody Map<String, String> message){
        aggregatorService.addUser(message);
    }

    @GetMapping("/getUser")
    public void getUser(){

    }

    @PostMapping("/updateUser")
    public void updateUser(){

    }

}
