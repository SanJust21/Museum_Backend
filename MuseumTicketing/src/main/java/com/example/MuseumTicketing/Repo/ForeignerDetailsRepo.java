package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.ForeignerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ForeignerDetailsRepo extends JpaRepository<ForeignerDetails, Long> {


        Optional<ForeignerDetails> findBySessionId(String sessionId);

        Optional<ForeignerDetails> findByMobileNumber(String mobileNumber);

        ForeignerDetails findByPaymentid(String paymentid);

        boolean existsByPaymentid(String paymentId);

        Optional<ForeignerDetails> findByOrderId(String orderId);


    Optional<ForeignerDetails> findByticketId(String ticketId);

    List<ForeignerDetails> findByVisitDate(LocalDate currentDate);

    List<? extends Object> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);

    default int countVisitorsToDate(LocalDate currentDate) {
        return Optional.ofNullable(countVisitorsToDateOrNull(currentDate)).orElse(0);
    }

    @Query("SELECT SUM(p.numberOfAdults + p.numberOfChildren) FROM PublicDetails p WHERE p.visitDate <= :currentDate AND p.visitStatus = true")
    Integer countVisitorsToDateOrNull(LocalDate currentDate);

    @Query("SELECT COUNT(f) FROM ForeignerDetails f WHERE f.ticketId IS NOT NULL")
    Integer countTotalForeignerTicketsWithticketId();

    @Query("SELECT SUM(f.totalPrice) FROM ForeignerDetails f WHERE f.ticketId IS NOT NULL")
    Double calculateTotalForeignerIncome();

    default double safeCalculateTotalForeignerIncomeForMonth(int month, int year) {
        Double totalIncome = calculateTotalForeignerIncomeForMonth(month, year);
        return totalIncome != null ? totalIncome : 0;
    }

    @Query("SELECT SUM(p.totalPrice) FROM ForeignerDetails p WHERE MONTH(p.visitDate) = :month AND YEAR(p.visitDate) = :year AND p.ticketId IS NOT NULL")
    Double calculateTotalForeignerIncomeForMonth(@Param("month") int month, @Param("year") int year);
}



