package com.example.MuseumTicketing.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "_institution_details")
public class InstitutionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mobileNumber;

    private String type;

    private String email;

    private String sessionId;

    private String institutionName;

    private String district;

    private int numberOfStudents;

    private int numberOfTeachers;

    private LocalDate visitDate;

    private int totalPrice;

    private String orderId;

    private String paymentid;

    public InstitutionDetails(String mobileNumber, String type, String email, String sessionId, String institutionName, String district, int numberOfStudents, int numberOfTeachers, LocalDate visitDate, int totalPrice, String orderId, String paymentid) {
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.email = email;
        this.sessionId = sessionId;
        this.institutionName = institutionName;
        this.district = district;
        this.numberOfStudents = numberOfStudents;
        this.numberOfTeachers = numberOfTeachers;
        this.visitDate = visitDate;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.paymentid = paymentid;
    }

    public InstitutionDetails(Long id) {
        this.id = id;
    }

    public InstitutionDetails() {

    }
}

