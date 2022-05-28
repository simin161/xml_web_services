package com.agent.app.controllers;

import com.agent.app.security.TokenUtils;
import com.agent.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @GetMapping("/checkIfAdmin")
    public boolean checkIfAdmin(@RequestHeader("Authorization") HttpHeaders header){
        return userService.checkIfAdmin(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)));
    }
}
