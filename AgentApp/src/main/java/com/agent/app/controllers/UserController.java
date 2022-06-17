package com.agent.app.controllers;

import com.agent.app.security.TokenUtils;
import com.agent.app.service.UserService;
import com.agent.app.utility.QRModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
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

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        return userService.verify(code) ? "verify_success" : "verify_fail";
    }

    @PostMapping("/forgottenPassword")
    public boolean forgottenPassword(@RequestBody Map<String, String> email){
        return userService.forgottenPassword(email.get("email"));
    }

    @PostMapping("/changePassword")
    public boolean changePassword(@RequestHeader("Authorization") HttpHeaders header, @RequestBody Map<String, String> password){
        return userService.changePassword(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)), password.get("password"));
    }

    @PostMapping("/enable2FA")
    public QRModel enable2FA(@RequestHeader("Authorization") HttpHeaders header){
        QRModel model = new QRModel();
        String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        String email = tokenUtils.getUsernameFromToken(value);
        return userService.enable2FA(email, model);
    }
}
