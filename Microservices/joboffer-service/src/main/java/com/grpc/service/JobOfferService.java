package com.grpc.service;

import com.grpc.model.JobOffer;
import com.grpc.repository.JobOfferRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.joboffer.JobOfferCreationReturnValue;
import proto.joboffer.JobOfferServiceGrpc;
import proto.joboffer.Joboffer;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class JobOfferService extends JobOfferServiceGrpc.JobOfferServiceImplBase {

    @Override
    public void createJobOffer(proto.joboffer.JobOfferCreationParams createParams, StreamObserver<JobOfferCreationReturnValue> responseObserver){
        JobOfferCreationReturnValue jocrv;
        //pretpostavka: job offer cak i kada je isti moguce je da su u pitanju dva razlicita job offera?
        try{
            JobOffer jobOffer = new JobOffer(createParams.getPosition(), createParams.getJobDescription(), createParams.getDailyActivities(), createParams.getCandidateRequirements().getRequirementList());
            JobOfferRepository.getInstance().createJobOffer(jobOffer);
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("true").build();
        }catch(Exception e){
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("false").build();
        }
        responseObserver.onNext(jocrv);
        responseObserver.onCompleted();
    }

}
