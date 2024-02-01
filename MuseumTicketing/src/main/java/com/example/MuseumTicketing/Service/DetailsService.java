package com.example.MuseumTicketing.Service;

import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.Details;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Repo.DetailsRepo;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsService {


//    private final InstitutionDetailsService institutionDetailsService;
//    private final PublicDetailsService publicDetailsService;
//    private final InstitutionDetailsRepo institutionDetailsRepo;
//    private final PublicDetailsService publicDetailsService;
//
//    @Autowired
//    public DetailsService(InstitutionDetailsService institutionDetailsService, PublicDetailsService publicDetailsService) {
//        this.institutionDetailsService = institutionDetailsService;
//        this.publicDetailsService = publicDetailsService;
//    }
//
//    public void storeOrderIdBySessionId(String sessionId, String orderId) {
//        Optional<InstitutionDetails> institutionDetails = institutionDetailsService.findBySessionId(sessionId);
//        if (institutionDetails.isPresent()) {
//            institutionDetails.get().setOrderId(orderId);
//            institutionDetailsService.save(institutionDetails.get());
//        } else {
//            Optional<PublicDetails> publicDetails = publicDetailsRepo.findBySessionId(sessionId);
//            if (publicDetails.isPresent()) {
//                publicDetails.get().setOrderId(orderId);
//                publicDetailsService.save(publicDetails.get());
//            } else {
//                // Handle case where neither InstitutionDetails nor PublicDetails is found for the sessionId
//            }
//        }
//    }

//    public Object submitAdditionalDetails(String sessionId, String type, DetailsRequest detailsRequest) {
//        Optional<Details> optionalDetails = detailsRepo.findBySessionId(sessionId);
//        if (optionalDetails.isPresent()) {
//            Details details = optionalDetails.get();
//
//
//
//            if ("institution".equalsIgnoreCase(type)) {
//                details.setInstitutionName(detailsRequest.getInstitutionName());
//                details.setDistrict(detailsRequest.getDistrict());
//                details.setNumberOfStudents(detailsRequest.getNumberOfStudents());
//                details.setNumberOfTeachers(detailsRequest.getNumberOfTeachers());
//                details.setVisitDate(detailsRequest.getVisitDate());
//
//            } else if ("public".equalsIgnoreCase(type)) {
//                details.setNumberOfAdults(detailsRequest.getNumberOfAdults());
//                details.setNumberOfChildren(detailsRequest.getNumberOfChildren());
//                details.setVisitDate(detailsRequest.getVisitDate());
//            }
//
//            detailsRepo.save(details);
//            return details;
//        }
//        else
//            return "Details not found!";
//    }
//    public void updateDetails(String sessionId, String userType, DetailsRequest detailsRequest) {
//        Optional<Details> optionalDetails = detailsRepo.findBySessionId(sessionId);
//        if (optionalDetails.isPresent()) {
//            Details details = optionalDetails.get();
//
//
//            if ("institution".equalsIgnoreCase(userType)) {
//                details.setInstitutionName(detailsRequest.getInstitutionName());
//                details.setDistrict(detailsRequest.getDistrict());
//                details.setNumberOfStudents(detailsRequest.getNumberOfStudents());
//                details.setNumberOfTeachers(detailsRequest.getNumberOfTeachers());
//                details.setVisitDate(detailsRequest.getVisitDate());
//            } else if ("public".equalsIgnoreCase(userType)) {
//                details.setNumberOfAdults(detailsRequest.getNumberOfAdults());
//                details.setNumberOfChildren(detailsRequest.getNumberOfChildren());
//                details.setVisitDate(detailsRequest.getVisitDate());
//            }
//
//
//            detailsRepo.save(details);
//        } else {
//
//        }
//    }


}
