package com.example.MuseumTicketing.DTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DetailsRequest {

    private String sessionId;
    private String type;
    private String mobileNumber;
    private String email;


    // Fields for school details
    private String institutionName;
    private String district;
    private int numberOfStudents;
    private int numberOfTeachers;
    private LocalDate visitDate;

    // Fields for public details
    private String name;
    private int numberOfAdults;
    private int numberOfChildren;
   // private String category; //'public' or 'foreigner'
    private int totalPrice;
    private String paymentid;

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return name;
    }

//    public String getCategory() {
//        return category;
//    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setCategory(String category) {
//        this.category = category;
//    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getType() {
        return type;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getDistrict() {
        return district;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public int getNumberOfTeachers() {
        return numberOfTeachers;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public String getEmail() {
        return email;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setNumberOfTeachers(int numberOfTeachers) {
        this.numberOfTeachers = numberOfTeachers;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
