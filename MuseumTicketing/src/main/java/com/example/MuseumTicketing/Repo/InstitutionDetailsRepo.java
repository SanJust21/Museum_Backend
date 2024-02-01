package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.InstitutionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionDetailsRepo extends JpaRepository<InstitutionDetails, Long> {

    Optional<InstitutionDetails> findBySessionId(String sessionId);

    Optional<InstitutionDetails> findByMobileNumber(String mobileNumber);

    InstitutionDetails findByPaymentid(String paymentid);

    boolean existsByPaymentid(String paymentId);

    Optional<InstitutionDetails> findByOrderId(String orderId);
}
