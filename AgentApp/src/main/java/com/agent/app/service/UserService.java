package com.agent.app.service;

import com.agent.app.model.User;
import com.agent.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean addUser(Map<String, String> message) {
        if(userRepository.findByEmail(message.get("email")) != null)
            return false;

        User user = new User();
        user.setPassword(passwordEncoder.encode(message.get("password")));
        user.setEmail(message.get("email"));
        user.setFirstName(message.get("firstName"));
        user.setLastName(message.get("lastName"));
        userRepository.save(user);
        return true;
    }
}
