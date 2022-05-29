package com.vinsguru.grpc.service;

import com.vinsguru.grpc.mail.MailService;
import com.vinsguru.grpc.model.Education;
import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.model.WorkExperience;
import com.vinsguru.grpc.repository.UserRepository;
import com.vinsguru.grpc.utility.Tokens;
import io.grpc.stub.StreamObserver;
import net.bytebuddy.utility.RandomString;
import net.devh.boot.grpc.server.service.GrpcService;

import proto.user.*;

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;


import javax.mail.MessagingException;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Stream;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final MailService mailService = new MailService();

    @Override
    public void addUser(proto.user.AddUserParam addUserParam,
                        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        proto.user.Output output = null;
        proto.user.userReg request = proto.user.userReg.newBuilder().setEmail(addUserParam.getReg().getEmail()).setBirthDate(addUserParam.getReg().getBirthDate())
                .setFirstName(addUserParam.getReg().getFirstName()).setLastName(addUserParam.getReg().getLastName()).setUsername(addUserParam.getReg().getUsername())
                .setGender(addUserParam.getReg().getGender()).setPassword(addUserParam.getReg().getPassword()).build();
        if(UserRepository.getInstance().findUserByParam("email", request.getEmail()).isEmpty() && UserRepository.getInstance().findUserByParam("username",request.getUsername()).isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthDate());
                User u = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword(), request.getGender(), date);
                u.setActivated(false);
                u.setUserAPItoken("");
                setVerificationCode(RandomString.make(64), u);
                UserRepository.getInstance().insert(u);
                mailService.sendVerificationEmail(u, addUserParam.getUrl().getSiteURL());
                output = Output.newBuilder().setResult("true").build();
            } catch (ParseException e) {
                output = Output.newBuilder().setResult("false").build();
            } catch (MessagingException | UnsupportedEncodingException e) {
                output = Output.newBuilder().setResult("false").build();
                e.printStackTrace();
            } catch (com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException e) {
                e.printStackTrace();
            }
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
            output = Output.newBuilder().setResult(user.get(0).getEmail()).build();
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
    public void getUserByEmail(proto.user.InputForGetUserByEmail request, StreamObserver<Output> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(request.getEmail());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        proto.user.Output output;
        if(user == null){
            output = Output.newBuilder().build();
        }else {
            String s = formatter.format(user.getBirthday());
            output = Output.newBuilder()
                    .setEmail(user.getEmail())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setUsername(user.getUsername())
                    .setPassword(user.getPassword())
                    .setPrivateProfile(user.isPrivateProfile())
                    .setBirthday(s)
                    .setGender(user.getGender())
                    .setPhone(user.getPhone() == null ? "No information" : user.getPhone())
                    .setBiography(user.getBiography() == null ? "No information" : user.getBiography())
                    .setInterests(user.getInterests() == null ? "No information" : user.getInterests())
                    .setSkills(user.getSkills() == null ? "No information" : user.getSkills())
                    .setIsEnabled(String.valueOf(user.isActivated()))
                    .setResult(user.getId().toString())
                    .setUserAPIToken(user.getUserAPItoken())
                    .build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserById(InputID request, StreamObserver<Output> responseObserver) {
        User user = UserRepository.getInstance().findUserByUsersId(request.getId());
        proto.user.Output output;
        if(user == null){
            output = Output.newBuilder().build();
        }else {
            output = Output.newBuilder()
                    .setEmail(user.getEmail())
                    .setUsername(user.getUsername())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersIdByUsername(InputUsername request, StreamObserver<OutputId> responseObserver) {
        String userId = UserRepository.getInstance().findUserIdByUsername(request.getUsername());
        proto.user.OutputId output;
        output = OutputId.newBuilder().setUsersId(userId).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(updateUserInfoInput request, StreamObserver<OutputMessage> responseObserver) {
        if(checkIfUserExists(request.getEmail())){
            Date date1= null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthday());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            proto.user.OutputMessage output;
            User userToUpdate=new User(null,request.getFirstName(),request.getLastName(),request.getUsername(),request.getEmail(),request.getPassword(),request.getPrivateProfile(),
                    date1,request.getGender(),request.getPhone(),request.getBiography(),request.getInterests(),request.getSkills(),null,null);
            UserRepository.getInstance().update(userToUpdate);
            output = OutputMessage.newBuilder().setOutputMessage("success").build();
            responseObserver.onNext(output);
            responseObserver.onCompleted();
        } else {
            System.out.print("ovde se promenio mejl");
        }
    }

    @Override
    public void updateEducation(proto.user.InputUpdateEducation request, StreamObserver<OutputMessage> responseObserver) {
        Date from= null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd").parse(request.getFrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date to= null;
        try {
            to = new SimpleDateFormat("yyyy-MM-dd").parse(request.getTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proto.user.OutputMessage output;
        UserRepository.getInstance().updateEducation(request.getEmail(),new Education(null,request.getSchool(),request.getDegree(),request.getFieldOfStudy(),
                from,to));
        output = OutputMessage.newBuilder().setOutputMessage("success").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }



    @Override
    public void getEducationsUserByEmail(InputForGetUserByEmail request, StreamObserver<OutputEducations> responseObserver) {
        proto.user.OutputEducations output;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
      List<Education> educationList=UserRepository.getInstance().getEducationsUserByEmail(request.getEmail());

        List<OutputEducation> educations = new ArrayList<OutputEducation>();
        for(Education education : educationList){
            String from = formatter.format(education.getFrom());
            String to = formatter.format(education.getTo());
            OutputEducation ed = OutputEducation.newBuilder().setSchool(education.getSchool()).setDegree(education.getDegree()).setFieldOfStudy(education.getFieldOfStudy())
                    .setFrom(from).setTo(to).setId(education.getIdEducation().toString()).build();
            educations.add(ed);
        }

        output =  OutputEducations.newBuilder().addAllEducations(educations).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void updateWorkExperience(InputUpdateWorkExperience request, StreamObserver<OutputMessage> responseObserver) {
        Date from = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd").parse(request.getFrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date to = null;
        try {
            to = new SimpleDateFormat("yyyy-MM-dd").parse(request.getTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proto.user.OutputMessage output;
        UserRepository.getInstance().updateWorkExperience(request.getEmail(), new WorkExperience(request.getWorkPlace(), request.getWorkTitle(), from, to));
        output = OutputMessage.newBuilder().setOutputMessage("success").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
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
        List<User> searchedUsers = new ArrayList<User>();
        for(User u : searchedByFirstName){
            if(!searchedUsers.contains(u)){
                searchedUsers.add(u);
            }
        }
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

    @Override
    public void findUserIdByEmail(InputForGetUserByEmail request, StreamObserver<OutputId> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(request.getEmail());
        proto.user.OutputId output;
            output = OutputId.newBuilder().setUsersId(user.getId().toString()).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserEmailById(OutputId request, StreamObserver<InputForGetUserByEmail> responseObserver){
        User user = UserRepository.getInstance().findUserByUsersId(request.getUsersId());
            proto.user.InputForGetUserByEmail ifgube;
            ifgube = InputForGetUserByEmail.newBuilder().setEmail(user.getEmail()).build();
            responseObserver.onNext(ifgube);
            responseObserver.onCompleted();
    }

    @Override
    public void checkIfAccountIsPrivate(InputForGetUserByEmail request, StreamObserver<OutputBool> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(request.getEmail());
        proto.user.OutputBool output;
        if(user.isPrivateProfile())
            output = OutputBool.newBuilder().setPrivate(true).build();
        else
            output = OutputBool.newBuilder().setPrivate(false).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void getExperiencesByEmail(InputForGetUserByEmail request, StreamObserver<OutputExperiences> responseObserver) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        proto.user.OutputExperiences output;
        List<WorkExperience> experiencesList=UserRepository.getInstance().getWorkExperienceByEmail(request.getEmail());

        List<OutputExperience> experiences = new ArrayList<>();
        for(WorkExperience experience : experiencesList){
            String from = formatter.format(experience.getFrom());
            String to = formatter.format(experience.getTo());
            OutputExperience ed = OutputExperience.newBuilder().setWorkPlace(experience.getWorkPlace()).setWorkTitle(experience.getWorkTitle())
                    .setFrom(from).setTo(to).setId(experience.getIdExperience().toString()).build();
            experiences.add(ed);
        }

        output =  OutputExperiences.newBuilder().addAllExperiences(experiences).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    public boolean checkIfUserExists(String email){
        boolean retVal = false;
        User u = null;
        u = UserRepository.getInstance().findUserByEmail(email);
        if(u != null) {
            retVal = true;
        }
        return retVal;
    }

    @Override
    public void forgottenPasswordUpdate(ForgottenPasswordEmail email, StreamObserver<ForgottenPasswordReturnValue> responseObserver){
        ForgottenPasswordReturnValue fprv;
        boolean value = false;
        for(User u : UserRepository.getInstance().getAllUsers()){
            if(u.getEmail().equals(email.getEmail())){
                u.setPassword(email.getNewPassword());
                UserRepository.getInstance().updatePassword(u);
                MailService ms = new MailService();
                try{
                    ms.sendForgottenPasswordEmail(email.getEmail(), email.getNewPasswordForEmail());
                }catch(Exception e){
                    e.printStackTrace();
                }
                value = true;
                break;
            }
        }
        if(value)
            fprv = ForgottenPasswordReturnValue.newBuilder().setValue("true").build();
        else
            fprv = ForgottenPasswordReturnValue.newBuilder().setValue("false").build();
        responseObserver.onNext(fprv);
        responseObserver.onCompleted();
    }

    @Override
    public void verifyAccount(VerificationCode code, StreamObserver<VerificationReturnValue> responseObserver){
        VerificationReturnValue vrv;
        boolean value;
        User user = UserRepository.getInstance().findUserByVerificationCode(code.getVerificationCode());
        if(user == null || user.isActivated()){
            value = false;
        }else{
            value = activateAccount(user);
        }
        if(value){
            vrv = VerificationReturnValue.newBuilder().setReturnValue("true").build();
        }else{
            vrv = VerificationReturnValue.newBuilder().setReturnValue("false").build();
        }
        responseObserver.onNext(vrv);
        responseObserver.onCompleted();
    }

    private boolean activateAccount(User user){
        user.setActivated(true);
        setVerificationCode("", user);
        UserRepository.getInstance().activateAccount(user);
        return true;
    }

    private void setVerificationCode(String code, User user){
        user.setVerificationCode(code);
    }

    @Override
    public void passwordlessLogin(PasswordlessLogin plogin, StreamObserver<VerificationReturnValue> responseObserver) {
        VerificationReturnValue vrv;
        try{
        mailService.sendPasswordlessLoginEmail(plogin.getEmail(), plogin.getSiteURL());
        vrv = VerificationReturnValue.newBuilder().setReturnValue("true").build();
       }catch(Exception e){
           vrv = VerificationReturnValue.newBuilder().setReturnValue("false").build();
       }
        responseObserver.onNext(vrv);
        responseObserver.onCompleted();
    }

    @Override
    public void changePassword(PasswordChangeInput input, StreamObserver<PasswordChangeOutput> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(input.getEmail());
        if(user != null){
            org.springframework.security.crypto.password.PasswordEncoder encoder
                    = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
            if(encoder.matches(input.getOldPassword(),user.getPassword())){
                user.setPassword(input.getNewPassword());
                UserRepository.getInstance().updatePassword(user);
                PasswordChangeOutput pso = PasswordChangeOutput.newBuilder().setResult("true").build();
                responseObserver.onNext(pso);
            }
            else{
                PasswordChangeOutput pso = PasswordChangeOutput.newBuilder().setResult("false").build();
                responseObserver.onNext(pso);
            }
        }else{
            PasswordChangeOutput pso = PasswordChangeOutput.newBuilder().setResult("false").build();
            responseObserver.onNext(pso);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void findUserByAPItoken(FindUserByAPItokenInput input, StreamObserver<FindUserByAPItokenOutput> responseObserver) {
        User user = UserRepository.getInstance().findUserByAPItoken(input.getUserAPItoken());
        FindUserByAPItokenOutput output;
        if (user == null) {
            output = FindUserByAPItokenOutput.newBuilder().setResult("false").build();
        } else {
            output = FindUserByAPItokenOutput.newBuilder().setResult("true").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEducation(InputDeleting request, StreamObserver<OutputBool> responseObserver) {
        proto.user.OutputBool output;
        boolean response=UserRepository.getInstance().deleteEducation(request.getEmail(),request.getId());
        output = OutputBool.newBuilder().setPrivate(response).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void saveUserAPIToken(SaveUserAPITokenInput input, StreamObserver<SaveUserAPITokenOutput> responseObserver) {
        SaveUserAPITokenOutput output;
        try {
            User user = UserRepository.getInstance().findUserByEmail(input.getEmail());
            user.setUserAPItoken(input.getTokenValue());
            UserRepository.getInstance().updateTokenValue(user);
            mailService.sendUserAPITokenMail(input.getEmail(), input.getTokenValue());
            output = SaveUserAPITokenOutput.newBuilder().setValue("true").build();
        } catch (Exception e) {
            output = SaveUserAPITokenOutput.newBuilder().setValue("false").build();
            e.printStackTrace();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteExperience(InputDeleting request, StreamObserver<OutputBool> responseObserver) {
        proto.user.OutputBool output;
        boolean response=UserRepository.getInstance().deleteExperience(request.getEmail(),request.getId());
        output = OutputBool.newBuilder().setPrivate(response).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void getOldAPIToken(GetOldAPITokenInput input, StreamObserver<GetOldAPITokenOutput> responseObserver){
        GetOldAPITokenOutput goato;
        User user = UserRepository.getInstance().findUserByEmail(input.getEmail());
        if(user==null){
            goato = GetOldAPITokenOutput.newBuilder().setOldToken("").build();
        }else{
            goato = GetOldAPITokenOutput.newBuilder().setOldToken(user.getUserAPItoken()).build();
        }
        responseObserver.onNext(goato);
        responseObserver.onCompleted();
    }
}
