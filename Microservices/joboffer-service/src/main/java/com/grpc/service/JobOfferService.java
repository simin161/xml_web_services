package com.grpc.service;

import com.google.protobuf.Empty;
import com.grpc.model.JobOffer;
import com.grpc.repository.CompanyRepository;
import com.grpc.repository.JobOfferRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.joboffer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@GrpcService
public class JobOfferService extends JobOfferServiceGrpc.JobOfferServiceImplBase {

    @Override
    public void createJobOffer(proto.joboffer.JobOfferCreationParams createParams, StreamObserver<JobOfferCreationReturnValue> responseObserver){
        JobOfferCreationReturnValue jocrv;
        try{
            JobOffer jobOffer = new JobOffer(createParams.getPosition(), createParams.getJobDescription(), createParams.getDailyActivities(), createParams.getCandidateRequirements(), createParams.getCompanyName(), createParams.getUserApiToken());
            if(checkIfSameOfferExists(jobOffer))
                jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("false").build();
            else {
                JobOfferRepository.getInstance().createJobOffer(jobOffer);
                jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("true").build();
            }
        } catch(Exception e){
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("false").build();
        }
        responseObserver.onNext(jocrv);
        responseObserver.onCompleted();
    }

    @Override
    public void searchJobOffers(SearchParam param, StreamObserver<SearchReturnValue> responseObserver){
        SearchReturnValue srv;
        try{
            List<JobOffer> searchedOffers = JobOfferRepository.getInstance().searchJobOfferByParam("position", param.getParam());
            List<SearchedOffer> retVal = new ArrayList<>();
            for(JobOffer jo : searchedOffers){
                SearchedOffer so = SearchedOffer.newBuilder().setId(jo.getId().toString()).setCompanyName(jo.getCompanyName()).setDailyActivities(jo.getDailyActivities())
                        .setJobDescription(jo.getJobDescription()).setCandidateRequirements(jo.getCandidateRequirements()).setPosition(jo.getPosition()).build();
                retVal.add(so);
            }
            srv = SearchReturnValue.newBuilder().addAllOffer(retVal).build();
        }catch(Exception e){
            srv = SearchReturnValue.newBuilder().build();
            e.printStackTrace();
        }
        responseObserver.onNext(srv);
        responseObserver.onCompleted();
    }

    @Override
    public void updateJobOfferAPIToken(ChangeAPITokenInput input, StreamObserver<ChangeAPITokenOutput> responseObserver){
        ChangeAPITokenOutput cato;
        try{
            boolean ret = updateAPIToken(input.getUpdatedAPIToken(), input.getOldAPIToken());
            if(ret)
                cato = ChangeAPITokenOutput.newBuilder().setValue("true").build();
            else
                cato = ChangeAPITokenOutput.newBuilder().setValue("false").build();
        }catch(Exception e){
            cato = ChangeAPITokenOutput.newBuilder().setValue("false").build();
            e.printStackTrace();
        }
        responseObserver.onNext(cato);
        responseObserver.onCompleted();
    }

    private boolean updateAPIToken(String userAPIToken, String oldAPIToken){
        boolean retVal = false;
        try{
            for(JobOffer jobOffer : JobOfferRepository.getInstance().getAllJobOffers()){
                    if(jobOffer.getUserAPItoken().equals(oldAPIToken)){
                    jobOffer.setUserAPItoken(userAPIToken);
                    JobOfferRepository.getInstance().updateJobOfferAPIToken(jobOffer);
                }
            }
            retVal = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void getAllJobOffers(Empty input, StreamObserver<GetAllJobOffersOutput> responseObserver){
        GetAllJobOffersOutput gajoo;
        try{
            List<JobOffer> offers = JobOfferRepository.getInstance().getAllJobOffers();
            List<SearchedOffer> outputOffers = new ArrayList<>();
            for(JobOffer o : offers){
                SearchedOffer so = SearchedOffer.newBuilder().setJobDescription(o.getJobDescription())
                        .setCandidateRequirements(o.getCandidateRequirements())
                        .setDailyActivities(o.getDailyActivities())
                        .setCompanyName(o.getCompanyName())
                        .setId(o.getId().toString())
                        .setPosition(o.getPosition())
                        .build();
                outputOffers.add(so);
            }
            gajoo = GetAllJobOffersOutput.newBuilder().addAllOffer(outputOffers).build();
        }catch(Exception e){
            e.printStackTrace();
            gajoo = GetAllJobOffersOutput.newBuilder().build();
        }
        responseObserver.onNext(gajoo);
        responseObserver.onCompleted();
    }

    private boolean checkIfSameOfferExists(JobOffer jobOffer){
        for(JobOffer jo : JobOfferRepository.getInstance().getAllJobOffers()){
            if(jo.equals(jobOffer)){
                return true;
            }
        }
        return false;
    }
}
