package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @GetMapping("/getUser")
    public void getUser(){

    }

    @PostMapping("/updateUser")
    public void updateUser(){

    }

}
