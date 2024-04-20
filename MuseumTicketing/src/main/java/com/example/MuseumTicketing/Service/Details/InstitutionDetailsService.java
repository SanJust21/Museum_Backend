package com.example.MuseumTicketing.Service.Details;

import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstitutionDetailsService {

    private final InstitutionDetailsRepo institutionDetailsRepo;

    @Autowired
    public InstitutionDetailsService(InstitutionDetailsRepo institutionDetailsRepo) {
        this.institutionDetailsRepo = institutionDetailsRepo;
    }

    public Object submitAdditionalDetails(String sessionId, String mobileNumber, DetailsRequest detailsRequest) {
        Optional<InstitutionDetails> optionalDetails = institutionDetailsRepo.findByMobileNumber(mobileNumber);
        InstitutionDetails institutionDetails;

        if (optionalDetails.isPresent()) {
            institutionDetails = optionalDetails.get();
        } else {
            institutionDetails = new InstitutionDetails();
            institutionDetails.setMobileNumber(mobileNumber);
        }

        institutionDetails.setSessionId(sessionId);
        institutionDetails.setType(detailsRequest.getType());
        institutionDetails.setEmail(detailsRequest.getEmail());
        institutionDetails.setInstitutionName(detailsRequest.getInstitutionName());
        institutionDetails.setDistrict(detailsRequest.getDistrict());
        institutionDetails.setNumberOfStudents(detailsRequest.getNumberOfStudents());
        institutionDetails.setNumberOfTeachers(detailsRequest.getNumberOfTeachers());
        institutionDetails.setTotalPrice(detailsRequest.getTotalPrice());
        institutionDetails.setVisitDate(detailsRequest.getVisitDate());
       // institutionDetails.setMobileNumber(detailsRequest.getMobileNumber());

        institutionDetailsRepo.save(institutionDetails);
        return institutionDetails;
    }
}

