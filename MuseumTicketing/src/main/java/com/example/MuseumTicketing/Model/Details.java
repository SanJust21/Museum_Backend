package com.example.MuseumTicketing.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_details")
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mobileNumber;

    private String email;

    private String sessionId;

    private String institutionName;

    private String district;

    private int numberOfStudents;

    private int numberOfTeachers;

    private int numberOfAdults;

    private int numberOfChildren;

//    private int numberOfForeignAdult;
//
//    private int numberOfForeignChild;
//
//    private int totalPrice;

    private LocalDate visitDate;
}
