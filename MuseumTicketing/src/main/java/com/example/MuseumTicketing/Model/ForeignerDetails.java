package com.example.MuseumTicketing.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

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

        private double totalPrice;

        private LocalDate visitDate;

       // @NotNull
        private LocalDate bookDate;

        private String paymentid;

        private String orderId;

        private String ticketId;

        @Column(name = "visit_status", nullable = false, columnDefinition = "boolean default false")
        private boolean visitStatus;

        @Column(name = "payment_status", nullable = false, columnDefinition = "boolean default false")
        private boolean paymentStatus;

        public ForeignerDetails(String mobileNumber, String type, String email, String sessionId, String name, int numberOfAdults, int numberOfChildren, double totalPrice, LocalDate visitDate, String paymentid, String orderId, String ticketId, LocalDate bookDate, boolean visitStatus, boolean paymentStatus) {
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
            this.ticketId = ticketId;
            this.bookDate = bookDate;
            this.visitStatus = visitStatus;
            this.paymentStatus = paymentStatus;
        }

        public ForeignerDetails(Long id) {
            this.id = id;
        }

        public ForeignerDetails() {

        }
}
