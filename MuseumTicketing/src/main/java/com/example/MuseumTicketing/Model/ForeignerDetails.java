package com.example.MuseumTicketing.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "_foreigner_details")
public class ForeignerDetails {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String mobileNumber;

        private String type;

        private String email;

        private String sessionId;

        private String name;

        private int numberOfAdults;

        private int numberOfChildren;

        //private String category; // "public" or "foreigner"

        private int totalPrice;

        private LocalDate visitDate;

        private String paymentid;

        private String orderId;

        public ForeignerDetails(String mobileNumber, String type, String email, String sessionId, String name, int numberOfAdults, int numberOfChildren, int totalPrice, LocalDate visitDate, String paymentid, String orderId) {
            this.mobileNumber = mobileNumber;
            this.type = type;
            this.email = email;
            this.sessionId = sessionId;
            this.name = name;
            this.numberOfAdults = numberOfAdults;
            this.numberOfChildren = numberOfChildren;
            this.totalPrice = totalPrice;
            this.visitDate = visitDate;
            this.paymentid = paymentid;
            this.orderId = orderId;
        }

        public ForeignerDetails(Long id) {
            this.id = id;
        }

        public ForeignerDetails() {

        }
}
