package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.UserDto;
import com.vinsguru.grpc.helperModel.Authority;
import com.vinsguru.grpc.helperModel.Permission;
import com.vinsguru.grpc.helperModel.User;
import com.vinsguru.grpc.utility.Validation;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proto.user.InputForGetUserByEmail;
import proto.user.Output;
import proto.user.UserServiceGrpc;

import java.util.ArrayList;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();

        if(Validation.validateEmail(username))
            blockingStub = openChannelToUserService();
            InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(username).build();
            Output result=this.blockingStub.getUserByEmail(input);
        if(!result.getEmail().equals("")) {
            user.setEmail(result.getEmail());
            user.setPassword(result.getPassword());
            user.setEnabled(Boolean.parseBoolean(result.getIsEnabled()));
            user.setId(result.getResult());
            List<Authority> a = new ArrayList<>();
            Authority auth = new Authority("ROLE_REG_USER");
            Permission perm = new Permission("READ");
            List<Permission> p = new ArrayList<>();
            p.add(perm);
            a.add(auth);
            user.setAuthorities(a);
            user.setPerm(p);
        }
        else
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        return user;
    }
}
