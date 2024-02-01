package com.example.MuseumTicketing.Service;

import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicDetailsService {

    private final PublicDetailsRepo publicDetailsRepo;

    @Autowired
    public PublicDetailsService(PublicDetailsRepo publicDetailsRepo) {
        this.publicDetailsRepo = publicDetailsRepo;
    }

    public Object submitAdditionalDetails(String sessionId, String mobileNumber, DetailsRequest detailsRequest) {
        Optional<PublicDetails> optionalDetails = publicDetailsRepo.findByMobileNumber(mobileNumber);
        PublicDetails publicDetails;

        if (optionalDetails.isPresent()) {
            publicDetails = optionalDetails.get();
        } else {
            publicDetails = new PublicDetails();
            publicDetails.setMobileNumber(mobileNumber);
        }

        publicDetails.setSessionId(sessionId);
        publicDetails.setName(detailsRequest.getName());
        publicDetails.setEmail(detailsRequest.getEmail());
        publicDetails.setNumberOfAdults(detailsRequest.getNumberOfAdults());
        publicDetails.setNumberOfChildren(detailsRequest.getNumberOfChildren());
        publicDetails.setType(detailsRequest.getType());
        publicDetails.setTotalPrice(detailsRequest.getTotalPrice());
        publicDetails.setVisitDate(detailsRequest.getVisitDate());
       // publicDetails.setPaymentid(detailsRequest.getPaymentid());


        publicDetailsRepo.save(publicDetails);
        return publicDetails;
    }
}
