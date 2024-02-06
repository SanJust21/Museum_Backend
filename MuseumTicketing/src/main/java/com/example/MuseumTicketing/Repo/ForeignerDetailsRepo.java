package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.ForeignerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ForeignerDetailsRepo extends JpaRepository<ForeignerDetails, Long> {


        Optional<ForeignerDetails> findBySessionId(String sessionId);

        Optional<ForeignerDetails> findByMobileNumber(String mobileNumber);

        ForeignerDetails findByPaymentid(String paymentid);

        boolean existsByPaymentid(String paymentId);

        Optional<ForeignerDetails> findByOrderId(String orderId);


    }



