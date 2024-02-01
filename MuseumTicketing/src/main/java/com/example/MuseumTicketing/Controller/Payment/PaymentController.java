package com.example.MuseumTicketing.Controller.Payment;
import com.example.MuseumTicketing.DTO.Payment.OrderRequest;
import com.example.MuseumTicketing.DTO.Payment.OrderResponse;
import com.example.MuseumTicketing.DTO.Payment.VerifyPaymentRequest;
import com.example.MuseumTicketing.Model.InstitutionDetails;
import com.example.MuseumTicketing.Model.PublicDetails;
import com.example.MuseumTicketing.Repo.InstitutionDetailsRepo;
import com.example.MuseumTicketing.Repo.PublicDetailsRepo;
import com.example.MuseumTicketing.Service.Payment.PaymentService;
import com.razorpay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {


    private static String razorpayKeyId = "rzp_test_Lh738g2oARGFbD";


    private static String razorpayKeySecret = "iOSGwx2YAmHsl2dNuzfi1bSa";

    private static final String currency = "INR";

    private PaymentService paymentService;

    private InstitutionDetailsRepo institutionDetailsRepo;
    private PublicDetailsRepo publicDetailsRepo;
    @Autowired
    public PaymentController(
            PaymentService paymentService,
            InstitutionDetailsRepo institutionDetailsRepo,
            PublicDetailsRepo publicDetailsRepo) {
        this.paymentService = paymentService;
        this.institutionDetailsRepo = institutionDetailsRepo;
        this.publicDetailsRepo = publicDetailsRepo;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/create-order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            int amount = orderRequest.getAmount();
            String sessionId = orderRequest.getSessionId();

            String orderId = paymentService.createOrder(amount);
            System.out.println(orderId);


            // Search the Institution and Public tables by sessionId
            Optional<InstitutionDetails> institutionDetails = institutionDetailsRepo.findBySessionId(sessionId);
            Optional<PublicDetails> publicDetails = publicDetailsRepo.findBySessionId(sessionId);

            InstitutionDetails institutionDetailsEntity = institutionDetails.orElse(null);
            PublicDetails publicDetailsEntity = publicDetails.orElse(null);

            if (institutionDetailsEntity != null) {
                institutionDetailsEntity.setOrderId(orderId);
                institutionDetailsRepo.save(institutionDetailsEntity);
            } else if (publicDetailsEntity != null) {
                publicDetailsEntity.setOrderId(orderId);
                publicDetailsRepo.save(publicDetailsEntity);
            } else {
                return ResponseEntity.badRequest().body("No corresponding details found for sessionId: " + sessionId);
            }
            OrderResponse orderResponse = new OrderResponse(razorpayKeyId, amount*100, currency, orderId, sessionId);
            System.out.println(orderResponse);
            return ResponseEntity.ok(orderResponse);
        } catch (RazorpayException e) {
            e.printStackTrace();
            String errorMessage = "Failed to create order. " + e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/verify-payment")
    public ResponseEntity<Object> verifyPayment(
            @RequestBody VerifyPaymentRequest verifyPaymentRequest) {
        try {

            String orderId = verifyPaymentRequest.getOrderId();
            String paymentId = verifyPaymentRequest.getPaymentId();
            String signature = verifyPaymentRequest.getSignature();

            boolean paymentVerified = paymentService.verifyPayment(orderId, paymentId, signature);

            if (paymentVerified) {
                // Store paymentId in the corresponding table based on orderId
                Optional<InstitutionDetails> institutionDetails = institutionDetailsRepo.findByOrderId(orderId);
                Optional<PublicDetails> publicDetails = publicDetailsRepo.findByOrderId(orderId);

                InstitutionDetails institutionDetailsEntity = institutionDetails.orElse(null);
                PublicDetails publicDetailsEntity = publicDetails.orElse(null);

                // Update the paymentId based on the type of details
                if (institutionDetailsEntity != null) {
                    institutionDetailsEntity.setPaymentid(paymentId);
                    institutionDetailsRepo.save(institutionDetailsEntity);
                } else if (publicDetailsEntity != null) {
                    publicDetailsEntity.setPaymentid(paymentId);
                    publicDetailsRepo.save(publicDetailsEntity);
                } else {
                    return ResponseEntity.badRequest().body("No corresponding details found for orderId: " + orderId);
                }
                return ResponseEntity.ok("Payment successful. Order ID: " + orderId + ", Payment ID: " + paymentId);
            } else {
                return ResponseEntity.badRequest().body("Payment verification failed.");
            }
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Payment verification failed. " + e.getMessage());
        }
    }

}
