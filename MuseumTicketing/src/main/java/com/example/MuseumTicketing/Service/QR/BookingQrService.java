package com.example.MuseumTicketing.Service.QR;

import com.example.MuseumTicketing.DTO.QR.BookingQrRequest;
import com.example.MuseumTicketing.DTO.QR.QrCodeResponse;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
import com.example.MuseumTicketing.Service.InstitutionDetailsService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookingQrService {

    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private InstitutionDetailsService institutionDetailsService;

    @Autowired
    private PublicDetailsRepo publicDetailsRepo;

    @Autowired
    private InstitutionDetailsRepo institutionDetailsRepo;

   // public byte[] generateAndFetchQrCode(BookingQrRequest bookingQrRequest) throws WriterException, IOException {
//   public QrCodeResponse generateAndFetchQrCode(BookingQrRequest bookingQrRequest) throws WriterException, IOException {
//
//        String userType = determineUserType(bookingQrRequest.getPaymentid());
//
//        String qrCodeDetails;
//        if ("institution".equalsIgnoreCase(bookingQrRequest.getType())) {
//            InstitutionDetails institutionDetails = institutionDetailsRepo.findByPaymentid(bookingQrRequest.getPaymentid());
//            qrCodeDetails = createBookingInfo(institutionDetails);
//        } else {
//            PublicDetails publicDetails = publicDetailsRepo.findByPaymentid(bookingQrRequest.getPaymentid());
//            qrCodeDetails = createBookingInfo(publicDetails);
//        }
//
//        // Generate QR code
//        //return qrCodeService.generateQrCode(qrCodeDetails);
//       byte[] qrCodeImage = qrCodeService.generateQrCode(qrCodeDetails);
//
//       // Create and return the DTO
//       QrCodeResponse response = new QrCodeResponse();
//       response.setQrCodeImage(qrCodeImage);
//       response.setUserDetails(qrCodeDetails);
//       return response;
//    }

    public QrCodeResponse generateAndFetchQrCode(BookingQrRequest bookingQrRequest) throws WriterException, IOException {
        String paymentId = bookingQrRequest.getPaymentid();

        // Search in InstitutionDetails
        InstitutionDetails institutionDetails = findInstitutionDetails(paymentId);
        if (institutionDetails != null) {
            String qrCodeDetails = createBookingInfo(institutionDetails);
            return generateQrCodeResponse(qrCodeDetails);
        }

        // If not found in InstitutionDetails, search in PublicDetails
        PublicDetails publicDetails = findPublicDetails(paymentId);
        if (publicDetails != null) {
            String qrCodeDetails = createBookingInfo(publicDetails);
            return generateQrCodeResponse(qrCodeDetails);
        }

        // If paymentId is not found in either table
        return generateErrorResponse("Details not found for given paymentId");
    }


    private QrCodeResponse generateQrCodeResponse(String qrCodeDetails) throws WriterException, IOException {
        byte[] qrCodeImage = qrCodeService.generateQrCode(qrCodeDetails);

        QrCodeResponse response = new QrCodeResponse();
        response.setQrCodeImage(qrCodeImage);
        response.setUserDetails(qrCodeDetails);
        return response;
    }

    private InstitutionDetails findInstitutionDetails(String paymentId) {
        return institutionDetailsRepo.findByPaymentid(paymentId);
    }

    private PublicDetails findPublicDetails(String paymentId) {
        return publicDetailsRepo.findByPaymentid(paymentId);
    }

    private QrCodeResponse generateErrorResponse(String errorMessage) {
        QrCodeResponse response = new QrCodeResponse();
        response.setErrorMessage(errorMessage);
        return response;
    }


    private String createBookingInfo(InstitutionDetails institutionDetails) {

        return String.format(
                "Name of Institution: %s, Students: %d, Teachers: %d, Date: %s, Amount: %d, Payment ID: %s",
                institutionDetails.getInstitutionName(),
                institutionDetails.getNumberOfStudents(),
                institutionDetails.getNumberOfTeachers(),
                institutionDetails.getVisitDate(),
                institutionDetails.getTotalPrice(),
                institutionDetails.getPaymentid()
        );
    }

    private String createBookingInfo(PublicDetails publicDetails) {

        return String.format(
                "Name: %s, Adults: %d, Children: %d, Date: %s, Amount: %d, Payment ID: %s",
                publicDetails.getName(),
                publicDetails.getNumberOfAdults(),
                publicDetails.getNumberOfChildren(),
                publicDetails.getVisitDate(),
                publicDetails.getTotalPrice(),
                publicDetails.getPaymentid()
        );
    }

    private String determineUserType(String paymentId) {
        // Check if paymentId exists in institution or public table
        if (institutionDetailsRepo.existsByPaymentid(paymentId)) {
            return "institution";
        } else if (publicDetailsRepo.existsByPaymentid(paymentId)) {
            return "public";
        } else {
            // Handle the case when paymentId is not found in either table
            throw new IllegalArgumentException("Invalid paymentId: " + paymentId);
        }
    }
}