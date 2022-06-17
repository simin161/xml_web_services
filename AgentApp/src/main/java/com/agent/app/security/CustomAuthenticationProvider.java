package com.agent.app.security;

import com.agent.app.model.User;
import com.agent.app.repository.UserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.sonatype.aether.repository.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;


}
