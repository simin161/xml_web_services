package com.agent.app.controllers;

import com.agent.app.model.User;
import com.agent.app.model.UserTokenState;
import com.agent.app.security.DefaultMFATokenManager;
import com.agent.app.security.TokenUtils;
import com.agent.app.security.auth.JwtAuthenticationRequest;
import com.agent.app.service.CompanyService;
import com.agent.app.service.CustomUserDetailsService;
import com.agent.app.service.UserService;
import com.agent.app.utility.LoggingStrings;
import lombok.extern.slf4j.Slf4j;
import com.agent.app.utility.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DefaultMFATokenManager mfaTokenManager;

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/registerCompany")
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    public boolean registerCompany(@RequestHeader("Authorization") HttpHeaders header, @RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        message.put("email",tokenUtils.getUsernameFromToken(value));
        return companyService.registerCompany(message);
    }

    @PostMapping("/logIn")
    public ResponseEntity<UserTokenState> logIn(@RequestBody JwtAuthenticationRequest cred,
                                                       HttpServletResponse response) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(cred.getEmail(),
                            cred.getPassword()));

            // Ubaci korisnika u trenutni security kontekst
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kreiraj token za tog korisnika
            User user = (User) authentication.getPrincipal();
            try{
                if(user.isUsing2FA()){
                    if(!mfaTokenManager.verifyTotp(cred.getCode(), user.getSecret())){
                        throw new Exception();
                    }
                }
            }catch(Exception e){
                log.error(LoggingStrings.getAuthenticationFailed("com.agent.app.controllers.RegistrationController", e.toString()));
                return null;
            }

            String jwt = tokenUtils.generateToken(user.getEmail());
            int expiresIn = tokenUtils.getExpiredIn();

            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }catch(Exception e){
            log.error(LoggingStrings.getAuthenticationFailed("com.agent.app.controllers.RegistrationController", e.toString()));
        }
        return null;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody Map<String, String> message){
        //TODO: dodati validacije
        return userService.addUser(message);
    }
}
