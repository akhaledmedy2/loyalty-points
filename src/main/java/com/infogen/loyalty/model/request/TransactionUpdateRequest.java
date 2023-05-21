package com.infogen.loyalty.model.request;

import lombok.Data;

@Data
public class TransactionUpdateRequest {
    private String transaction_id;
    private int transaction_status;
    private double amount;
}