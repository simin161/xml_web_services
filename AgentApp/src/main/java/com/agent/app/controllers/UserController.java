package com.agent.app.controllers;

import com.agent.app.security.TokenUtils;
import com.agent.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/updateApiToken")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public boolean updateApiToken(@RequestHeader("Authorization") HttpHeaders header, @RequestBody String apiToken){
        return userService.updateApiToken(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)), apiToken);
    }

    @PostMapping("/passwordless")
    public boolean passwordlessLogin(@RequestBody Map<String, String> email){
        return userService.passwordlessLogin(email.get("email"));
    }

    @PostMapping("/forgottenPassword")
    public boolean forgottenPassword(@RequestBody String email){
        return userService.forgottenPassword(email);
    }
}
