package com.example.MuseumTicketing.Service.AdminScanner;

import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Model.ForeignerDetails;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Model.ScannedDetails;
import com.example.MuseumTicketing.Repo.ForeignerDetailsRepo;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
import com.example.MuseumTicketing.Repo.ScannedDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScannerService {


        private final ForeignerDetailsRepo foreignerDetailsRepo;
        private final InstitutionDetailsRepo institutionDetailsRepo;
        private final PublicDetailsRepo publicDetailsRepo;

        private final ScannedDetailsRepo scannedDetailsRepo;

    @Autowired
    public ScannerService(ForeignerDetailsRepo foreignerDetailsRepo, InstitutionDetailsRepo institutionDetailsRepo, PublicDetailsRepo publicDetailsRepo, ScannedDetailsRepo scannedDetailsRepo) {
        this.foreignerDetailsRepo = foreignerDetailsRepo;
        this.institutionDetailsRepo = institutionDetailsRepo;
        this.publicDetailsRepo = publicDetailsRepo;
        this.scannedDetailsRepo = scannedDetailsRepo;
    }

    public ResponseEntity<?> identifyUserAndGetDetails(String bookingId, LocalDateTime scannedTime) {
        // Check if ticket is already scanned
        Optional<ScannedDetails> existingScan = scannedDetailsRepo.findByTicketId(bookingId);
        if (existingScan.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket already scanned.");
        }

        // Retrieve details based on bookingId
        Optional<ForeignerDetails> foreignerDetails = foreignerDetailsRepo.findByBookingId(bookingId);
        Optional<InstitutionDetails> institutionDetails = institutionDetailsRepo.findByBookingId(bookingId);
        Optional<PublicDetails> publicDetails = publicDetailsRepo.findByBookingId(bookingId);


        if (foreignerDetails.isEmpty() && institutionDetails.isEmpty() && publicDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the provided ticket ID.");
        }

        ScannedDetails scannedDetails = new ScannedDetails();
        scannedDetails.setTicketId(bookingId);
        scannedDetails.setScannedTime(scannedTime);

//        if (foreignerDetails.isPresent() && !foreignerDetails.get().getVisitDate().isEqual(LocalDate.now())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Visit date is different.");
//        } else if (institutionDetails.isPresent() && !institutionDetails.get().getVisitDate().isEqual(LocalDate.now())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Visit date is different.");
//        } else if (publicDetails.isPresent() && !publicDetails.get().getVisitDate().isEqual(LocalDate.now())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Visit date is different.");
//        }

        if (foreignerDetails.isPresent()) {
            DetailsRequest detailsRequest = convertToDetailsRequest(foreignerDetails.get());
            ForeignerDetails foreigner = foreignerDetails.get();
            scannedDetails.setName(detailsRequest.getName());
            scannedDetails.setType(detailsRequest.getType());
            foreigner.setVisitStatus(true);
            foreignerDetailsRepo.save(foreigner);
        } else if (institutionDetails.isPresent()) {
            DetailsRequest detailsRequest = convertToDetailsRequest(institutionDetails.get());
            InstitutionDetails institution = institutionDetails.get();
            scannedDetails.setName(detailsRequest.getInstitutionName());
            scannedDetails.setType(detailsRequest.getType());
            institution.setVisitStatus(true);
            institutionDetailsRepo.save(institution);
        } else if (publicDetails.isPresent()) {
            DetailsRequest detailsRequest = convertToDetailsRequest(publicDetails.get());
            PublicDetails publicDetail = publicDetails.get();
            scannedDetails.setName(detailsRequest.getName());
            scannedDetails.setType(detailsRequest.getType());
            publicDetail.setVisitStatus(true);
            publicDetailsRepo.save(publicDetail);
        }

        scannedDetailsRepo.save(scannedDetails);

        // Return appropriate details request based on bookingId
        if (foreignerDetails.isPresent()) {
            return ResponseEntity.ok(convertToDetailsRequest(foreignerDetails.get()));
        } else if (institutionDetails.isPresent()) {
            return ResponseEntity.ok(convertToDetailsRequest(institutionDetails.get()));
        } else if (publicDetails.isPresent()) {
            return ResponseEntity.ok(convertToDetailsRequest(publicDetails.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the provided ticket ID.");
    }
//    public ResponseEntity<?> identifyUserAndGetDetails(String bookingId, LocalDateTime scannedTime) {
//
//        Optional<ScannedDetails> existingScan = scannedDetailsRepo.findByTicketId(bookingId);
//        if (existingScan.isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket already scanned.");
//        }
//
//        ScannedDetails scannedDetails = new ScannedDetails();
//        scannedDetails.setTicketId(bookingId);
//        scannedDetails.setScannedTime(scannedTime);
//        scannedDetailsRepo.save(scannedDetails);
//
//        // Use bookingId to find the corresponding details
//        Optional<ForeignerDetails> foreignerDetails = foreignerDetailsRepo.findByBookingId(bookingId);
//        if (foreignerDetails.isPresent()) {
//            return ResponseEntity.ok(convertToDetailsRequest(foreignerDetails.get()));
//        }
//
//        Optional<InstitutionDetails> institutionDetails = institutionDetailsRepo.findByBookingId(bookingId);
//        if (institutionDetails.isPresent()) {
//            return ResponseEntity.ok(convertToDetailsRequest(institutionDetails.get()));
//        }
//
//        Optional<PublicDetails> publicDetails = publicDetailsRepo.findByBookingId(bookingId);
//        if (publicDetails.isPresent()) {
//            return ResponseEntity.ok(convertToDetailsRequest(publicDetails.get()));
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the provided ticket ID.");
//    }

    public DetailsRequest convertToDetailsRequest(ForeignerDetails foreignerDetails) {

        DetailsRequest detailsRequest = new DetailsRequest();

        ScannedDetails scannedDetails = new ScannedDetails();

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

        ScannedDetails scannedDetails = new ScannedDetails();

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

        ScannedDetails scannedDetails = new ScannedDetails();

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

    public List<ScannedDetails> getScannedDetailsForToday() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Retrieve scanned details for the current date
        List<ScannedDetails> scannedDetailsList = scannedDetailsRepo.findByScannedTimeBetween(
                currentDate.atStartOfDay(), // Start of the current date
                currentDate.atStartOfDay().plusDays(1) // Start of the next date (exclusive)
        );

        return scannedDetailsList;
    }

}

