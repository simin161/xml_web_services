package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.UserDTO;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.user.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;


@Service
public class AggregatorService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public String addUser(Map<String,String> message) {
        blockingStub = openChannelToUserService();
        Input input = Input.newBuilder().setEmail(message.get("email")).setFirstName(message.get("firstName"))
                .setLastName(message.get("lastName")).setPassword(message.get("password")).setUsername(message.get("username")).build();
        return this.blockingStub.addUser(input).getResult();
    }

    public String invalidateUser(String value){
        blockingStub = openChannelToUserService();
        Input2 input = Input2.newBuilder().setAccessToken(value).build();
        return this.blockingStub.invalidateUser(input).getResult();
    }

    public String logInUser(Map<String, String> message) {
        blockingStub = openChannelToUserService();
        Input1 input = Input1.newBuilder().setEmail(message.get("email")).setPassword(message.get("password")).build();
        return this.blockingStub.logInUser(input).getResult();
    }

    public List<UserDTO> getUsers(){

        com.google.protobuf.Empty request = null;
        blockingStub = openChannelToUserService();
        //List<Input> retval =  this.blockingStub.getAllUsers(request).getUserList();

        List<UserDTO> retVal = new ArrayList<UserDTO>();

        for(Input i : this.blockingStub.getAllUsers(request).getUserList()){
            UserDTO userDTO = new UserDTO(i.getUsername(), i.getFirstName(), i.getLastName(), i.getPassword(), i.getEmail());
            retVal.add(userDTO);
        }

        return retVal;
    }

    public List<UserDTO> searchUsers(String param) {

        blockingStub = openChannelToUserService();
        List<UserDTO> retVal = new ArrayList<UserDTO>();
        InputSearch is = InputSearch.newBuilder().setParam(param).build();

        for(Input i : this.blockingStub.searchUsers(is).getUserList()){
            UserDTO userDTO = new UserDTO(i.getUsername(), i.getFirstName(), i.getLastName(), i.getPassword(), i.getEmail());
            retVal.add(userDTO);
        }

        return retVal;
    }
}
