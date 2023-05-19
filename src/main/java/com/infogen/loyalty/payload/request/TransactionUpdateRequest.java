package com.infogen.loyalty.payload.request;

import lombok.Data;

@Data
public class TransactionUpdateRequest {
    private String transaction_id;
    private int transaction_status;
    private double amount;
}