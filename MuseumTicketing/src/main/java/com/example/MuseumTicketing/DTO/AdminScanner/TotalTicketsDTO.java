package com.example.MuseumTicketing.DTO.AdminScanner;

import lombok.Data;

@Data
public class TotalTicketsDTO {
    private int totalPublicTickets;
    private int totalInstitutionTickets;
    private int totalForeignerTickets;

    public TotalTicketsDTO(int totalPublicTickets, int totalInstitutionTickets, int totalForeignerTickets) {
        this.totalPublicTickets = totalPublicTickets;
        this.totalInstitutionTickets = totalInstitutionTickets;
        this.totalForeignerTickets = totalForeignerTickets;
    }
}
