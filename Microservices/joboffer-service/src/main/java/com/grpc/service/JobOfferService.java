package com.grpc.service;

import com.grpc.model.JobOffer;
import com.grpc.repository.CompanyRepository;
import com.grpc.repository.JobOfferRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.joboffer.*;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class JobOfferService extends JobOfferServiceGrpc.JobOfferServiceImplBase {

    @Override
    public void createJobOffer(proto.joboffer.JobOfferCreationParams createParams, StreamObserver<JobOfferCreationReturnValue> responseObserver){
        JobOfferCreationReturnValue jocrv;
        //pretpostavka: job offer cak i kada je isti moguce je da su u pitanju dva razlicita job offera?
        try{
            List<String> reqs = new ArrayList<>();
            String []parts = createParams.getCandidateRequirements().split(", ");
            for(String p : parts){
                reqs.add(p);
            }
            JobOffer jobOffer = new JobOffer(createParams.getPosition(), createParams.getJobDescription(), createParams.getDailyActivities(), reqs, createParams.getCompanyName());
            JobOfferRepository.getInstance().createJobOffer(jobOffer);
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("true").build();
        }catch(Exception e){
            jocrv = JobOfferCreationReturnValue.newBuilder().setRetVal("false").build();
        }
        responseObserver.onNext(jocrv);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompanyNamesForEmail(CompanyOwnerEmail email, StreamObserver<CompanyNamesForEmail> responseObserver){
        CompanyNamesForEmail cnfe;
        try{
            List<String> companyNamesForEmail = CompanyRepository.getInstance().getUserCompanyNames(email.getEmail());
            StringBuilder retVal= new StringBuilder();
            for(String name : companyNamesForEmail){
                retVal.append(name).append(", ");
            }
            String ret = retVal.toString();
            ret = ret.substring(0, ret.length()-2);
            cnfe = CompanyNamesForEmail.newBuilder().setCompanyNames(ret).build();

        }catch(Exception e){
            cnfe = CompanyNamesForEmail.newBuilder().build();
            e.printStackTrace();
        }
        responseObserver.onNext(cnfe);
        responseObserver.onCompleted();
    }

}
