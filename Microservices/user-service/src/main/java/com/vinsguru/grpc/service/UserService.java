package com.vinsguru.grpc.service;

import com.vinsguru.grpc.model.Education;
import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.model.WorkExperience;
import com.vinsguru.grpc.repository.UserRepository;
import com.vinsguru.grpc.utility.Tokens;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import proto.user.*;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import proto.user.*;

import java.util.ArrayList;

import java.util.List;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void addUser(proto.user.Input request,
                        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        proto.user.Output output;
        if(UserRepository.getInstance().findUserByParam("email",request.getEmail()).isEmpty()) {
            UserRepository.getInstance().insert(new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword()));
            output = Output.newBuilder().setResult(Tokens.generateToken(request.getUsername(), request.getEmail())).build();
        }else
            output = Output.newBuilder().setResult("false").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void logInUser(proto.user.Input1 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        List<User> user = UserRepository.getInstance().findUserByParam("email",request.getEmail());
        proto.user.Output output;
        if(!user.isEmpty() && user.get(0).getPassword().equals(request.getPassword()))
            output = Output.newBuilder().setResult(Tokens.generateToken(user.get(0).getUsername(), user.get(0).getEmail())).build();
        else
            output = Output.newBuilder().setResult("false").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void invalidateUser(proto.user.Input2 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        proto.user.Output output = Output.newBuilder().setResult("").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override

    public void getUserByEmail(InputForGetUserByEmail request, StreamObserver<Output> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(request.getEmail());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(user.getBirthday());
        proto.user.Output output;
        output = Output.newBuilder()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setPrivateProfile(user.isPrivateProfile())
                .setBirthday(s)
                .setGender(user.getGender())
                .setPhone(user.getPhone())
                .setBiography(user.getBiography())
                .setInterests(user.getInterests())
                .setSkills(user.getSkills())
                .build();

        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(updateUserInfoInput request, StreamObserver<OutputMessage> responseObserver) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthday());
        proto.user.OutputMessage output;
        User userToUpdate=new User(request.getFirstName(),request.getLastName(),request.getUsername(),request.getEmail(),request.getPassword(),request.getPrivateProfile(),
                date1,request.getGender(),request.getPhone(),request.getBiography(),request.getInterests(),request.getSkills(),null,null);
        UserRepository.getInstance().update(userToUpdate);
        output = OutputMessage.newBuilder().setOutputMessage("success").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void updateEducation(InputUpdateEducation request, StreamObserver<OutputMessage> responseObserver) throws ParseException {
        Date from=new SimpleDateFormat("yyyy-MM-dd").parse(request.getFrom());
        Date to=new SimpleDateFormat("yyyy-MM-dd").parse(request.getTo());
        proto.user.OutputMessage output;
        UserRepository.getInstance().updateEducation(request.getEmail(),new Education(request.getSchool(),request.getDegree(),request.getFieldOfStudy(),
                from,to));
        output = OutputMessage.newBuilder().setOutputMessage("success").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }



    @Override
    public void getEducationsUserByEmail(InputForGetUserByEmail request, StreamObserver<OutputEducations> responseObserver) {
        proto.user.OutputEducations output=null;
        List<Education> educationList=UserRepository.getInstance().getEducationsUserByEmail(request.getEmail());


        for(Education education : educationList){
         output =  OutputEducations.newBuilder().setEducations(educationList.indexOf(education),OutputEducations.newBuilder().getEducations(0)).build();
        }

        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void updateWorkExperience(InputUpdateWorkExperience request, StreamObserver<OutputMessage> responseObserver) throws ParseException {
        Date from=new SimpleDateFormat("yyyy-MM-dd").parse(request.getFrom());
        Date to=new SimpleDateFormat("yyyy-MM-dd").parse(request.getTo());
        proto.user.OutputMessage output;
        UserRepository.getInstance().updateWorkExperience(request.getEmail(),new WorkExperience(request.getWorkPlace(),request.getWorkTitle(),from,to));
        output = OutputMessage.newBuilder().setOutputMessage("success").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    public void getAllUsers(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<proto.user.Output2> responseObserver){

        List<User> documentedUsers = UserRepository.getInstance().getAllUsers();
        List<Input> inputs = new ArrayList<Input>();
        for(User u : documentedUsers){
            Input i = Input.newBuilder().setUsername(u.getUsername()).setLastName(u.getLastName()).setPassword(u.getPassword()).setEmail(u.getEmail()).setFirstName(u.getFirstName()).build();
            inputs.add(i);
        }
        proto.user.Output2 output2;
        output2 = Output2.newBuilder().addAllUser(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();

    }

    @Override
    public void searchUsers(InputSearch request, StreamObserver<Output2> responseObserver) {

        List<User> searchedByFirstName = UserRepository.getInstance().findUserByParam("firstName", request.getParam());
        List<User> searchedByLastName = UserRepository.getInstance().findUserByParam("lastName", request.getParam());
        List<User> searchedByUsername = UserRepository.getInstance().findUserByParam("username", request.getParam());

        List<User> searchedUsers = new ArrayList<User>(searchedByFirstName);

        for(User u : searchedByLastName){
            if(!searchedUsers.contains(u)){
                searchedUsers.add(u);
            }
        }

        for(User u : searchedByUsername){
            if(!searchedUsers.contains(u)){
                searchedUsers.add(u);
            }
        }

        List<Input> inputs = new ArrayList<Input>();
        for(User u : searchedUsers){
            Input i = Input.newBuilder().setUsername(u.getUsername()).setLastName(u.getLastName()).setPassword(u.getPassword()).setEmail(u.getEmail()).setFirstName(u.getFirstName()).build();
            inputs.add(i);
        }
        proto.user.Output2 output2;
        output2 = Output2.newBuilder().addAllUser(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();


    }
}
