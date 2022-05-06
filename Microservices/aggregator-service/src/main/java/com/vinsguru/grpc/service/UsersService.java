package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.EducationDto;
import   com.vinsguru.grpc.dto.*;
import com.vinsguru.grpc.dto.WorkExperienceDto;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;


@Service
public class UsersService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;



    public String addUser(Map<String,String> message) {
        blockingStub = openChannelToUserService();
        userReg input = userReg.newBuilder().setEmail(message.get("email")).setFirstName(message.get("firstName"))
                .setLastName(message.get("lastName")).setPassword(message.get("password")).setUsername(message.get("username")).setGender(message.get("gender"))
                .setBirthDate(message.get("birthDate")).build();
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

    public UserDto getUserByEmail(String email){
        blockingStub = openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(email).build();
        Output result=this.blockingStub.getUserByEmail(input);

        return new UserDto(result.getFirstName(),result.getLastName(),result.getUsername(),result.getEmail(),result.getPassword(),result.getPrivateProfile()
        , result.getBirthday(),result.getGender(),result.getPhone(),result.getBiography(),result.getInterests(),result.getSkills());
    }

    public String updateUser(Map<String, String> user) {
        blockingStub = openChannelToUserService();
        boolean p=false;
        if(user.get("private").equals("true")) p=true;
        updateUserInfoInput input = updateUserInfoInput.newBuilder()
                .setEmail(user.get("email"))
                .setFirstName(user.get("firstName"))
                .setLastName(user.get("lastName"))
                .setUsername(user.get("username"))
                .setPrivateProfile(p)
                .setBirthday(user.get("birthday"))
                .setGender(user.get("gender"))
                .setPhone(user.get("phone"))
                .setBiography(user.get("biography"))
                .setInterests(user.get("interests"))
                .setSkills(user.get("skills"))
                .build();
        return this.blockingStub.updateUser(input).getOutputMessage();
    }

    public String updateEducation(EducationDto educationDto) {
        blockingStub = openChannelToUserService();
        InputUpdateEducation input = InputUpdateEducation.newBuilder()
                .setEmail(educationDto.getEmail())
                .setSchool(educationDto.getSchool())
                .setDegree(educationDto.getDegree())
                .setFieldOfStudy(educationDto.getFieldOfStudy())
                .setFrom(educationDto.getFrom())
                .setTo(educationDto.getTo())
                .build();
        return this.blockingStub.updateEducation(input).getOutputMessage();
    }

    public List<EducationDto> getEducationsUserByEmail(String email) {
        blockingStub = openChannelToUserService();
        List<EducationDto> educationDtos = new ArrayList<>();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder()
                .setEmail(email)
                .build();

        for (OutputEducation outputEducation:  this.blockingStub.getEducationsUserByEmail(input).getEducationsList()){
            educationDtos.add(new EducationDto(email,outputEducation.getSchool(),outputEducation.getDegree(),outputEducation.getFieldOfStudy(),
                    outputEducation.getFrom(),outputEducation.getTo()));
        }
        return educationDtos;
    }

    public String updateWorkExperiences(WorkExperienceDto workExperienceDto) {
        blockingStub = openChannelToUserService();
        InputUpdateWorkExperience input = InputUpdateWorkExperience.newBuilder()
                .setWorkPlace(workExperienceDto.getWorkPlace())
                .setWorkTitle(workExperienceDto.getWorkTitle())
                .setFrom(workExperienceDto.getFrom())
                .setTo(workExperienceDto.getTo())
                .setEmail(workExperienceDto.getEmail())
                .build();
        return this.blockingStub.updateWorkExperience(input).getOutputMessage();

    }

        public List<DisplayUserDto> getUsers () {

            com.google.protobuf.Empty request = null;
            blockingStub = openChannelToUserService();

            List<DisplayUserDto> retVal = new ArrayList<DisplayUserDto>();

            for (Input i : this.blockingStub.getAllUsers(request).getUserList()) {
                DisplayUserDto userDTO = new DisplayUserDto(i.getUsername(), i.getFirstName(), i.getLastName(), i.getEmail());
                retVal.add(userDTO);
            }

            return retVal;
        }

        public List<DisplayUserDto> searchUsers (String param){

            blockingStub = openChannelToUserService();
            List<DisplayUserDto> retVal = new ArrayList<DisplayUserDto>();
            InputSearch is = InputSearch.newBuilder().setParam(param).build();

            for (Input i : this.blockingStub.searchUsers(is).getUserList()) {
                DisplayUserDto userDTO = new DisplayUserDto(i.getUsername(), i.getFirstName(), i.getLastName(), i.getEmail());
                retVal.add(userDTO);
            }

            return retVal;

        }

    public List<WorkExperienceDto> getExperiencesByEmail(String email) {
        blockingStub = openChannelToUserService();
        List<WorkExperienceDto> experienceDtos = new ArrayList<>();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder()
                .setEmail(email)
                .build();

        for (OutputExperience output:  this.blockingStub.getExperiencesByEmail(input).getExperiencesList()){
            experienceDtos.add(new WorkExperienceDto(output.getWorkPlace(),output.getWorkTitle(),output.getFrom(),output.getTo()));
        }
        return experienceDtos;
    }



}
