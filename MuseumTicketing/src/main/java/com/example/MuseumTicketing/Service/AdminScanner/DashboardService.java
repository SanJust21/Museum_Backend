package com.example.MuseumTicketing.Service.AdminScanner;


import com.example.MuseumTicketing.DTO.AdminScanner.CategoryVisitorDTO;
import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.ForeignerDetails;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Model.ScannedDetails;
import com.example.MuseumTicketing.Repo.ForeignerDetailsRepo;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    private final PublicDetailsRepo publicDetailsRepo;

    private final InstitutionDetailsRepo institutionDetailsRepo;

    private final ForeignerDetailsRepo foreignerDetailsRepo;

    @Autowired
    public DashboardService(PublicDetailsRepo publicDetailsRepo, InstitutionDetailsRepo institutionDetailsRepo, ForeignerDetailsRepo foreignerDetailsRepo) {
        this.publicDetailsRepo = publicDetailsRepo;
        this.institutionDetailsRepo = institutionDetailsRepo;
        this.foreignerDetailsRepo = foreignerDetailsRepo;
    }

    public List<DetailsRequest> getCurrentDayDetails() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Retrieve details for each category for the current date
        List<DetailsRequest> publicDetails = getPublicDetailsForDate(currentDate);
        List<DetailsRequest> institutionDetails = getInstitutionDetailsForDate(currentDate);
        List<DetailsRequest> foreignerDetails = getForeignerDetailsForDate(currentDate);

        // Combine all details into a single list
        List<DetailsRequest> allDetails = new ArrayList<>();
        allDetails.addAll(publicDetails);
        allDetails.addAll(institutionDetails);
        allDetails.addAll(foreignerDetails);

        return allDetails;
    }

    private List<DetailsRequest> getPublicDetailsForDate(LocalDate currentDate) {
        // Retrieve public details for the current date
        List<PublicDetails> publicDetailsList = publicDetailsRepo.findByVisitDate(currentDate);

        // Convert PublicDetails entities to DetailsRequest DTOs
        List<DetailsRequest> detailsRequests = new ArrayList<>();
        for (PublicDetails publicDetails : publicDetailsList) {
            detailsRequests.add(convertToDetailsRequest(publicDetails));
        }

        return detailsRequests;
    }

    private List<DetailsRequest> getInstitutionDetailsForDate(LocalDate currentDate) {
        // Retrieve institution details for the current date
        List<InstitutionDetails> institutionDetailsList = institutionDetailsRepo.findByVisitDate(currentDate);

        // Convert InstitutionDetails entities to DetailsRequest DTOs
        List<DetailsRequest> detailsRequests = new ArrayList<>();
        for (InstitutionDetails institutionDetails : institutionDetailsList) {
            detailsRequests.add(convertToDetailsRequest(institutionDetails));
        }

        return detailsRequests;
    }

    private List<DetailsRequest> getForeignerDetailsForDate(LocalDate currentDate) {
        // Retrieve foreigner details for the current date
        List<ForeignerDetails> foreignerDetailsList = foreignerDetailsRepo.findByVisitDate(currentDate);

        // Convert ForeignerDetails entities to DetailsRequest DTOs
        List<DetailsRequest> detailsRequests = new ArrayList<>();
        for (ForeignerDetails foreignerDetails : foreignerDetailsList) {
            detailsRequests.add(convertToDetailsRequest(foreignerDetails));
        }

        return detailsRequests;
    }

    public DetailsRequest convertToDetailsRequest(ForeignerDetails foreignerDetails) {

        DetailsRequest detailsRequest = new DetailsRequest();

        detailsRequest.setSessionId(foreignerDetails.getSessionId());
        detailsRequest.setType(foreignerDetails.getType());
        detailsRequest.setMobileNumber(foreignerDetails.getMobileNumber());
        detailsRequest.setEmail(foreignerDetails.getEmail());
        detailsRequest.setName(foreignerDetails.getName());
        detailsRequest.setNumberOfAdults(foreignerDetails.getNumberOfAdults());
        detailsRequest.setNumberOfChildren(foreignerDetails.getNumberOfChildren());
        detailsRequest.setTotalPrice(foreignerDetails.getTotalPrice());
        detailsRequest.setVisitDate(foreignerDetails.getVisitDate());
        detailsRequest.setBookDate(foreignerDetails.getBookDate());
        detailsRequest.setPaymentid(foreignerDetails.getPaymentid());
        detailsRequest.setVisitStatus(foreignerDetails.isVisitStatus());


        return detailsRequest;
    }

    public DetailsRequest convertToDetailsRequest(InstitutionDetails institutionDetails) {

        DetailsRequest detailsRequest = new DetailsRequest();

        detailsRequest.setSessionId(institutionDetails.getSessionId());
        detailsRequest.setType(institutionDetails.getType());
        detailsRequest.setMobileNumber(institutionDetails.getMobileNumber());
        detailsRequest.setEmail(institutionDetails.getEmail());
        detailsRequest.setInstitutionName(institutionDetails.getInstitutionName());
        detailsRequest.setDistrict(institutionDetails.getDistrict());
        detailsRequest.setNumberOfStudents(institutionDetails.getNumberOfStudents());
        detailsRequest.setNumberOfTeachers(institutionDetails.getNumberOfTeachers());
        detailsRequest.setTotalPrice(institutionDetails.getTotalPrice());
        detailsRequest.setVisitDate(institutionDetails.getVisitDate());
        detailsRequest.setBookDate(institutionDetails.getBookDate());
        detailsRequest.setPaymentid(institutionDetails.getPaymentid());
        detailsRequest.setVisitStatus(institutionDetails.isVisitStatus());

        return detailsRequest;
    }
    public DetailsRequest convertToDetailsRequest(PublicDetails publicDetails) {

        DetailsRequest detailsRequest = new DetailsRequest();

        detailsRequest.setSessionId(publicDetails.getSessionId());
        detailsRequest.setType(publicDetails.getType());
        detailsRequest.setMobileNumber(publicDetails.getMobileNumber());
        detailsRequest.setEmail(publicDetails.getEmail());
        detailsRequest.setName(publicDetails.getName());
        detailsRequest.setNumberOfAdults(publicDetails.getNumberOfAdults());
        detailsRequest.setNumberOfChildren(publicDetails.getNumberOfChildren());
        detailsRequest.setNumberOfSeniors(publicDetails.getNumberOfSeniors());
        detailsRequest.setTotalPrice(publicDetails.getTotalPrice());
        detailsRequest.setVisitDate(publicDetails.getVisitDate());
        detailsRequest.setBookDate(publicDetails.getBookDate());
        detailsRequest.setPaymentid(publicDetails.getPaymentid());
        detailsRequest.setVisitStatus(publicDetails.isVisitStatus());

        return detailsRequest;
    }

    public List<CategoryVisitorDTO> getTotalVisitorsForCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        List<CategoryVisitorDTO> visitorDTOs = new ArrayList<>();

        // Get total visitors for public category
        int totalPublicVisitors = getTotalVisitorsForCategory(publicDetailsRepo.findByVisitDate(currentDate), "Public");
        visitorDTOs.add(new CategoryVisitorDTO("Public", totalPublicVisitors));

        // Get total visitors for institution category
        int totalInstitutionVisitors = getTotalVisitorsForCategory(institutionDetailsRepo.findByVisitDate(currentDate), "Institution");
        visitorDTOs.add(new CategoryVisitorDTO("Institution", totalInstitutionVisitors));

        // Get total visitors for foreigner category
        int totalForeignerVisitors = getTotalVisitorsForCategory(foreignerDetailsRepo.findByVisitDate(currentDate), "Foreigner");
        visitorDTOs.add(new CategoryVisitorDTO("Foreigner", totalForeignerVisitors));

        return visitorDTOs;
    }
    public List<CategoryVisitorDTO> getTotalVisitorsForWeek() {
        // Get the start date (most recent Monday)
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Get the end date (following Sunday)
        LocalDate endDate = startDate.plusDays(6); // 6 days ahead to include the whole week

        return getTotalVisitorsForWeek(startDate, endDate);
    }

    public List<CategoryVisitorDTO> getTotalVisitorsForWeek(LocalDate startDate, LocalDate endDate) {
        // Get total visitors for each category within the specified week
        List<CategoryVisitorDTO> visitorDTOs = new ArrayList<>();
        visitorDTOs.add(new CategoryVisitorDTO("Public", getTotalVisitorsForCategory(publicDetailsRepo.findByVisitDateBetween(startDate, endDate), "Public")));
        visitorDTOs.add(new CategoryVisitorDTO("Institution", getTotalVisitorsForCategory(institutionDetailsRepo.findByVisitDateBetween(startDate, endDate), "Institution")));
        visitorDTOs.add(new CategoryVisitorDTO("Foreigner", getTotalVisitorsForCategory(foreignerDetailsRepo.findByVisitDateBetween(startDate, endDate), "Foreigner")));
        return visitorDTOs;
    }


    private int getTotalVisitorsForCategory(List<? extends Object> detailsList, String category) {
        int totalVisitors = 0;
        for (Object details : detailsList) {
            if (details instanceof PublicDetails && category.equals("Public")) {
                if (((PublicDetails) details).isVisitStatus()) {
                    totalVisitors += ((PublicDetails) details).getNumberOfAdults() + ((PublicDetails) details).getNumberOfChildren();
                }
            } else if (details instanceof InstitutionDetails && category.equals("Institution")) {
                if (((InstitutionDetails) details).isVisitStatus()) {
                    totalVisitors += ((InstitutionDetails) details).getNumberOfTeachers() + ((InstitutionDetails) details).getNumberOfStudents();
                }
            } else if (details instanceof ForeignerDetails && category.equals("Foreigner")) {
                if (((ForeignerDetails) details).isVisitStatus()) {
                    totalVisitors += ((ForeignerDetails) details).getNumberOfAdults() + ((ForeignerDetails) details).getNumberOfChildren();
                }
            }
        }
        return totalVisitors;
    }

}

