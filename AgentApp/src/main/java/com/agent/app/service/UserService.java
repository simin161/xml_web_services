package com.agent.app.service;

import com.agent.app.model.Authority;
import com.agent.app.model.User;
import com.agent.app.repository.AuthorityRepository;
import com.agent.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;
    public boolean addUser(Map<String, String> message) {
        if(userRepository.findByEmail(message.get("email")) != null)
            return false;

        User user = new User();
        user.setPassword(passwordEncoder.encode(message.get("password")));
        user.setEmail(message.get("email"));
        user.setFirstName(message.get("firstName"));
        user.setLastName(message.get("lastName"));
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authorityRepository.findById(1L).orElse(null));
        user.setAuthorities(authorityList);
        userRepository.save(user);
        return true;
    }

    public boolean checkIfAdmin(String email) {
        User user = userRepository.findByEmail(email);
        List<Authority> a = (List<Authority>) user.getAuthorities();
        for( Authority aa : a){
            if(aa.getName().equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }
}
