package com.grpc.service;

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
        //pretpostavka: job offer cak i kada je isti moguce je da su u pitanju dva razlicita job offera?
        try{
            JobOffer jobOffer = new JobOffer(createParams.getPosition(), createParams.getJobDescription(), createParams.getDailyActivities(), createParams.getCandidateRequirements(), createParams.getCompanyName(), createParams.getUserApiToken());
            JobOfferRepository.getInstance().createJobOffer(jobOffer);
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("true").build();
        }catch(Exception e){
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

}
