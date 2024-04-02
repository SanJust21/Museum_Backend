package com.example.MuseumTicketing.Service.Payment;
import com.razorpay.*;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static String razorpayKeyId = "rzp_test_Lh738g2oARGFbD";


    private static String razorpayKeySecret = "iOSGwx2YAmHsl2dNuzfi1bSa";

    private static final String currency = "INR";


    public String createOrder(double amount) throws RazorpayException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject options = new JSONObject();
            options.put("amount", amount * 100); // Razorpay expects amount in paisa
            options.put("currency", currency);
            options.put("receipt", "order_rcptid_" + System.currentTimeMillis());
            options.put("payment_capture", 1); // Auto capture

            Order order = razorpayClient.orders.create(options);
            return order.get("id");
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    public boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException {
        try {

            if (!verifySignature(orderId, paymentId, signature)) {

                throw new RazorpayException("Signature verification failed.");
            }

            //Signature is verified, proceed with Razorpay API verification
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            // Fetch payment details by payment ID
            Payment payment = razorpayClient.payments.fetch(paymentId);

            // Check the payment status
            return "captured".equals(payment.get("status"));
        } catch (RazorpayException e) {
            e.printStackTrace();
            throw new RazorpayException("Payment verification failed.", e);
        }
    }
    public boolean verifySignature(String orderId, String paymentId, String signature) {
        String secret = "iOSGwx2YAmHsl2dNuzfi1bSa";

        // Concatenate orderId, paymentId, and your secret
        String generatedSignature = orderId + "|" + paymentId;
        generatedSignature = HmacUtils.hmacSha256Hex(secret, generatedSignature);

        // Compare generated signature with the received signature
        return generatedSignature.equals(signature);
    }

}