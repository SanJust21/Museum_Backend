//package com.example.MuseumTicketing.Service.Email;
//
//import com.example.MuseumTicketing.DTO.QR.BookingQrRequest;
//import com.example.MuseumTicketing.DTO.QR.QrCodeResponse;
//import com.example.MuseumTicketing.Model.InstitutionDetails;
//import com.example.MuseumTicketing.Model.PublicDetails;
//import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
//import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMultipart;
//
//
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private PublicDetailsRepo publicDetailsRepo;
//
//    @Autowired
//    private InstitutionDetailsRepo institutionDetailsRepo;
//
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//
//    public void sendQrCodeEmail(String paymentid, QrCodeResponse qrCodeResponse) {
//        String subject = "Your Booking QR Code";
//        String text = "Thank you for booking! Please find your QR code below.";
//
//        try {
//            String to = getEmailByPaymentid(paymentid);
//
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//            helper.setFrom(new InternetAddress(senderEmail));
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text, true);
//
//            // Embed the QR code image in the email
//            helper.addInline("qrCodeImage", new ByteArrayResource(qrCodeResponse.getQrCodeImage()), "image/png");
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//
//            }
//    }
//    private String getEmailByPaymentid(String paymentId) {
//
//
//         if (institutionDetailsRepo.existsByPaymentid(paymentId)) {
//             return institutionDetailsRepo.findByPaymentid(paymentId).getEmail();
//         } else if (publicDetailsRepo.existsByPaymentid(paymentId)) {
//             return publicDetailsRepo.findByPaymentid(paymentId).getEmail();
//         }
//        return "Email not found!";
//    }
//}
//
