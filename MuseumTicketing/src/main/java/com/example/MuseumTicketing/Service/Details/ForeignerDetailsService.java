package com.example.MuseumTicketing.Service.Details;

import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.ForeignerDetails;
import com.example.MuseumTicketing.Repo.ForeignerDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ForeignerDetailsService {

        private final ForeignerDetailsRepo foreignerDetailsRepo;

        @Autowired
        public ForeignerDetailsService(ForeignerDetailsRepo foreignerDetailsRepo) {
            this.foreignerDetailsRepo = foreignerDetailsRepo;
        }

        public Object submitAdditionalDetails(String sessionId, String mobileNumber, DetailsRequest detailsRequest) {
            Optional<ForeignerDetails> optionalDetails = foreignerDetailsRepo.findByMobileNumber(mobileNumber);
            ForeignerDetails foreignerDetails;

            if (optionalDetails.isPresent()) {
                foreignerDetails = optionalDetails.get();
            } else {
                foreignerDetails = new ForeignerDetails();
                foreignerDetails.setMobileNumber(mobileNumber);
            }

            foreignerDetails.setSessionId(sessionId);
            foreignerDetails.setName(detailsRequest.getName());
            foreignerDetails.setEmail(detailsRequest.getEmail());
            foreignerDetails.setNumberOfAdults(detailsRequest.getNumberOfAdults());
            foreignerDetails.setNumberOfChildren(detailsRequest.getNumberOfChildren());
            foreignerDetails.setType(detailsRequest.getType());
            foreignerDetails.setTotalPrice(detailsRequest.getTotalPrice());
            foreignerDetails.setVisitDate(detailsRequest.getVisitDate());
            // publicDetails.setMobileNumber(detailsRequest.getMobileNumber());


            foreignerDetailsRepo.save(foreignerDetails);
            return foreignerDetails;
        }

}
