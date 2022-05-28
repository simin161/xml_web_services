package com.vinsguru.grpc.service;

import com.google.protobuf.Empty;
import com.vinsguru.grpc.dto.JobOfferDto;
import com.vinsguru.grpc.utility.Validation;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proto.joboffer.*;
import proto.user.UserServiceGrpc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToJobOfferService;
import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;

@Service
public class JobOfferService {

    @GrpcClient("joboffer-service")
    private JobOfferServiceGrpc.JobOfferServiceBlockingStub blockingStub;

    @Autowired
    private UsersService userService;

    public boolean createJobOffer(JobOfferDto jobOfferDto){
        //TODO: dodati validacije na kreiranje ponude
        boolean retVal= false;
        try {
            if(Validation.checkIfEmptyJobOffer(jobOfferDto))
                return false;
           /* if(Validation.validateNonBrackets(jobOfferDto.getUserAPItoken()) || Validation.validateNonBrackets(jobOfferDto.getJobDescription())
            || Validation.validateNonBrackets(jobOfferDto.getPosition()) || Validation.validateNonBrackets(jobOfferDto.getCompanyName())
            || Validation.validateNonBrackets(jobOfferDto.getDailyActivities()) || Validation.validateNonBrackets(jobOfferDto.getCandidateRequirements()))*/
            if(findUserByAPItoken(jobOfferDto.getUserAPItoken())){
                blockingStub = openChannelToJobOfferService();
                JobOfferCreationParams jocp = JobOfferCreationParams.newBuilder().setJobDescription(jobOfferDto.getJobDescription())
                            .setCandidateRequirements(jobOfferDto.getCandidateRequirements()).setCompanyName(jobOfferDto.getCompanyName())
                            .setDailyActivities(jobOfferDto.getDailyActivities()).setPosition(jobOfferDto.getPosition())
                            .setUserApiToken(jobOfferDto.getUserAPItoken()).build();
                String ret = blockingStub.createJobOffer(jocp).getRetVal();
                if (ret.equals("true"))
                    retVal = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return retVal;
    }

    public List<JobOfferDto> searchJobOffers(String param){
        List<JobOfferDto> searchedOffers = new ArrayList<>();
        try{
            blockingStub = openChannelToJobOfferService();
            SearchParam sp = SearchParam.newBuilder().setParam(param).build();
            SearchReturnValue srv = blockingStub.searchJobOffers(sp);
            for(SearchedOffer so : srv.getOfferList()){
                JobOfferDto jod = new JobOfferDto(so.getPosition(), so.getJobDescription(), so.getDailyActivities(), so.getCandidateRequirements(), so.getCompanyName(), so.getUserApiToken());
                searchedOffers.add(jod);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return searchedOffers;
    }

    private boolean findUserByAPItoken(String userAPItoken){
        return userService.findUserByAPItoken(userAPItoken);
    }

    public boolean updateJobOffers(String token, String oldToken) {
        try{
            blockingStub = openChannelToJobOfferService();
            ChangeAPITokenInput input = ChangeAPITokenInput.newBuilder().setUpdatedAPIToken(token).setOldAPIToken(oldToken).build();
            String ret = blockingStub.updateJobOfferAPIToken(input).getValue();
            return ret.equals("true");
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<JobOfferDto> getAllJobOffers(){
        List<JobOfferDto> jobOfferDtos = new ArrayList<>();
        try{
            blockingStub = openChannelToJobOfferService();
            List<SearchedOffer> offersOutput = blockingStub.getAllJobOffers(Empty.newBuilder().build()).getOfferList();
            for(SearchedOffer so : offersOutput){
                JobOfferDto jod = new JobOfferDto();
                jod.setCandidateRequirements(so.getCandidateRequirements());
                jod.setCompanyName(so.getCompanyName());
                jod.setJobDescription(so.getJobDescription());
                jod.setPosition(so.getPosition());
                jod.setUserAPItoken("");
                jod.setDailyActivities(so.getDailyActivities());
                jobOfferDtos.add(jod);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return jobOfferDtos;
    }
}
