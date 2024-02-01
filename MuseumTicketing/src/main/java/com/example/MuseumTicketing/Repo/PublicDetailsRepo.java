package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.PublicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicDetailsRepo extends JpaRepository<PublicDetails, Long> {

    Optional<PublicDetails> findBySessionId(String sessionId);

    Optional<PublicDetails> findByMobileNumber(String mobileNumber);

    PublicDetails findByPaymentid(String paymentid);

    boolean existsByPaymentid(String paymentId);

    Optional<PublicDetails> findByOrderId(String orderId);
}

