package com.example.MuseumTicketing.DTO.Payment;

import lombok.Data;

@Data
public class OrderRequest {

    private int amount;
    private String sessionId;
}
