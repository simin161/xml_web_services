package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.JobOfferDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.joboffer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToJobOfferService;

@Service
public class JobOfferService {

    @GrpcClient("joboffer-service")
    private JobOfferServiceGrpc.JobOfferServiceBlockingStub blockingStub;

    public boolean createJobOffer(JobOfferDto jobOfferDto){
        //TODO: dodati validacije na kreiranje ponude
        boolean retVal= false;
        try {
            blockingStub = openChannelToJobOfferService();
            JobOfferCreationParams jocp = JobOfferCreationParams.newBuilder().setJobDescription(jobOfferDto.getJobDescription())
                    .setCandidateRequirements(jobOfferDto.getCandidateRequirements()).setCompanyName(jobOfferDto.getCompanyName()).setDailyActivities(jobOfferDto.getDailyActivities())
                    .setPosition(jobOfferDto.getPosition()).setUserApiToken(jobOfferDto.getUserAPItoken()).build();
            String ret = blockingStub.createJobOffer(jocp).getRetVal();
            if (ret.equals("true"))
                retVal = true;
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
}
