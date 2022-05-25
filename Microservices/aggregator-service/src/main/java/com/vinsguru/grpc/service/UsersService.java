package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.EducationDto;
import   com.vinsguru.grpc.dto.*;
import com.vinsguru.grpc.dto.WorkExperienceDto;


import com.vinsguru.grpc.mail.MailService;
import com.vinsguru.grpc.security.TokenUtils;
import com.vinsguru.grpc.utility.Validation;
import jdk.jfr.Experimental;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proto.user.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;


@Service
public class UsersService {

    @Autowired
    private TokenUtils tokenUtils;

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(Map<String,String> message, String siteURL) {
        try {
            if (Validation.validatePassword(message.get("password")) && Validation.validateEmail(message.get("email")) &&
                    Validation.validateName(message.get("firstName")) && Validation.validateName(message.get("lastName"))
                    && Validation.validateUsername(message.get("username")) && Validation.validateName(message.get("gender"))) {
                blockingStub = openChannelToUserService();
                String password = passwordEncoder.encode(message.get("password"));
                userReg input = userReg.newBuilder().setEmail(message.get("email")).setFirstName(message.get("firstName"))
                        .setLastName(message.get("lastName")).setPassword(password).setUsername(message.get("username")).setGender(message.get("gender"))
                        .setBirthDate(message.get("birthDate")).build();
                SiteURL url = SiteURL.newBuilder().setSiteURL(siteURL).build();
                AddUserParam aup = AddUserParam.newBuilder().setReg(input).setUrl(url).build();
                return this.blockingStub.addUser(aup).getResult();
            }
        }catch(Exception e){
        }
        return "false";
    }

    public String logInUser(Map<String, String> message) {
        try {
            if(Validation.validateEmail(message.get("email"))) {
                blockingStub = openChannelToUserService();
                Input1 input = Input1.newBuilder().setEmail(message.get("email")).setPassword(message.get("password")).build();
                return this.blockingStub.logInUser(input).getResult();
            }
        }catch(Exception e){
        }
        return "false";
    }

    public UserDto getUserByEmail(String email){
        blockingStub = openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(email).build();
        Output result=this.blockingStub.getUserByEmail(input);

        UserDto userDto =  new UserDto(result.getFirstName(),result.getLastName(),result.getUsername(),result.getEmail(),result.getPrivateProfile()
        , result.getBirthday(),result.getGender(),result.getPhone(),result.getBiography(),result.getInterests(),result.getSkills(),result.getUserAPIToken());
        userDto.setEnabled(Boolean.parseBoolean(result.getIsEnabled()));
        return userDto;
    }

    public String updateUser(Map<String, String> user) {
        try{
            if (/*Validation.validatePassword(user.get("password")) && */ Validation.validateEmail(user.get("email")) &&
                    Validation.validateName(user.get("firstName")) && Validation.validateName(user.get("lastName"))
                    && Validation.validateUsername(user.get("username")) && Validation.validateName(user.get("gender"))
                    && Validation.validatePhone(user.get("phone")) && !Validation.validateNonBrackets(user.get("biography"))
                    && !Validation.validateNonBrackets("interests") && !Validation.validateNonBrackets(user.get("skills"))) {
                blockingStub = openChannelToUserService();
                boolean p = false;
                if (user.get("isPrivate").equals("true")) p = true;
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
        } catch(Exception e) {

        }
        return null;
    }

    public String updateEducation(EducationDto educationDto) {
        if(!Validation.validateNonBrackets(educationDto.getFieldOfStudy()) && !Validation.validateNonBrackets(educationDto.getDegree())
        && !Validation.validateNonBrackets(educationDto.getSchool()) && Validation.validateEmail(educationDto.getEmail())) {
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
        return null;
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
        if(!Validation.validateNonBrackets(workExperienceDto.getWorkPlace()) && !Validation.validateNonBrackets(workExperienceDto.getWorkTitle())
        && Validation.validateEmail(workExperienceDto.getEmail())) {
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
        return null;
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


    public boolean forgottenPassword(Map<String, String> email) {
        try {
            if (Validation.validateEmail(email.get("email"))) {
                blockingStub = openChannelToUserService();
                boolean retVal = false;
                String newPassword = String.valueOf(LocalDateTime.now().hashCode());
                newPassword = newPassword.replace('-', '0');
                newPassword = newPassword.substring(0, 6);
                String newPasswordForEmail = new String(newPassword);
                newPassword = passwordEncoder.encode(newPassword);
                ForgottenPasswordEmail fpe = ForgottenPasswordEmail.newBuilder().setEmail(email.get("email")).setNewPassword(newPassword).setNewPasswordForEmail(newPasswordForEmail).build();
                retVal = Boolean.parseBoolean(blockingStub.forgottenPasswordUpdate(fpe).getValue());

                return retVal;
            }
        }catch(Exception e){}
        return false;
    }

    public String verifyAccount(String code) {
        blockingStub = openChannelToUserService();
        boolean value = false;
        String retVal = "";
        VerificationCode vc = VerificationCode.newBuilder().setVerificationCode(code).build();
        String ret = blockingStub.verifyAccount(vc).getReturnValue();
        value = Boolean.parseBoolean(ret);
        if(value){
            retVal = "verified";
        }else{
            retVal = "error";
        }
        return retVal;
    }

    public boolean passwordlessLogin(Map<String, String> email, String siteURL) throws MessagingException, UnsupportedEncodingException {
        try {
            if (Validation.validateEmail(email.get("email"))) {

                //boolean value = false;
                //PasswordlessLogin pl = PasswordlessLogin.newBuilder().setEmail(email.get("email")).setSiteURL(siteURL).build();
                //value = Boolean.parseBoolean(blockingStub.passwordlessLogin(pl).getReturnValue());
                UserDto d = getUserByEmail(email.get("email"));
                if (d != null) {
                    blockingStub = openChannelToUserService();
                    String jwt = tokenUtils.generateToken(d.getEmail(), "ROLE_REG_USER");
                    int expiresIn = tokenUtils.getExpiredIn();
                    PasswordlessLogin pl = PasswordlessLogin.newBuilder().setEmail(d.getEmail()).setSiteURL(jwt).build();
                    VerificationReturnValue vrv = blockingStub.passwordlessLogin(pl);
                    return vrv.getReturnValue().equals("true");
                } else {
                    return false;
                }
            }
        }catch(Exception e){}
        return false;
    }

    public String changePassword(Map<String, String> message) {
        blockingStub = openChannelToUserService();
        PasswordChangeInput pI = PasswordChangeInput.newBuilder().setEmail(message.get("email"))
                .setNewPassword(passwordEncoder.encode(message.get("newPassword")))
                .setOldPassword(message.get("oldPassword")).build();
        return blockingStub.changePassword(pI).getResult();
    }

    public boolean findUserByAPItoken(String userAPItoken){
        boolean retVal = false;
        blockingStub = openChannelToUserService();
        FindUserByAPItokenInput input = FindUserByAPItokenInput.newBuilder().setUserAPItoken(userAPItoken).build();
        String ret = blockingStub.findUserByAPItoken(input).getResult();
        if(ret.equals("true"))
            retVal = true;
        return retVal;
    }

    public boolean saveGeneratedToken(String email, String token) {
        boolean retVal = false;
        blockingStub = openChannelToUserService();
        SaveUserAPITokenInput input = SaveUserAPITokenInput.newBuilder().setEmail(email).setTokenValue(token).build();
        String ret = blockingStub.saveUserAPIToken(input).getValue();
        if(ret.equals("true"))
            retVal = true;
        return retVal;
    }
}
