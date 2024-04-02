package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.InstitutionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionDetailsRepo extends JpaRepository<InstitutionDetails, Long> {

    Optional<InstitutionDetails> findBySessionId(String sessionId);

    Optional<InstitutionDetails> findByMobileNumber(String mobileNumber);

    InstitutionDetails findByPaymentid(String paymentid);

    boolean existsByPaymentid(String paymentId);

    Optional<InstitutionDetails> findByOrderId(String orderId);

    Optional<InstitutionDetails> findByticketId(String ticketId);

    List<InstitutionDetails> findByVisitDate(LocalDate currentDate);

    List<? extends Object> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);

    default int countVisitorsToDate(LocalDate currentDate) {
        return Optional.ofNullable(countVisitorsToDateOrNull(currentDate)).orElse(0);
    }

    @Query("SELECT SUM(p.numberOfTeachers + p.numberOfStudents) FROM InstitutionDetails p WHERE p.visitDate <= :currentDate AND p.visitStatus = true")
    Integer countVisitorsToDateOrNull(LocalDate currentDate);

    @Query("SELECT COUNT(i) FROM InstitutionDetails i WHERE i.ticketId IS NOT NULL")
    Integer countTotalInstitutionTicketsWithticketId();

    @Query("SELECT SUM(i.totalPrice) FROM InstitutionDetails i WHERE i.ticketId IS NOT NULL")
    Double calculateTotalInstitutionIncome();

    default double safeCalculateTotalInstitutionIncomeForMonth(int month, int year) {
        Double totalIncome = calculateTotalInstitutionIncomeForMonth(month, year);
        return totalIncome != null ? totalIncome : 0;
    }

    @Query("SELECT SUM(p.totalPrice) FROM InstitutionDetails p WHERE MONTH(p.visitDate) = :month AND YEAR(p.visitDate) = :year AND p.ticketId IS NOT NULL")
    Double calculateTotalInstitutionIncomeForMonth(@Param("month") int month, @Param("year") int year);
}
