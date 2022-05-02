package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.dto.UserDTO;
import com.vinsguru.grpc.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proto.user.Input;
import proto.user.User;
import reactor.core.publisher.Flux;

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

}
