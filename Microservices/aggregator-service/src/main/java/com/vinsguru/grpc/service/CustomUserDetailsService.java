package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.UserDto;
import com.vinsguru.grpc.helperModel.Authority;
import com.vinsguru.grpc.helperModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        UserDto d = userService.getUserByEmail(username);
        if(d != null) {
            user.setEmail(d.getEmail());
            user.setPassword(passwordEncoder.encode(d.getPassword()));
            List<Authority> a = new ArrayList<>();
            Authority auth = new Authority("ROLE_REG_USER");
            a.add(auth);
            user.setAuthorities(a);
        }
        else
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        return user;
    }
}
