package com.infogen.loyalty.model.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private double amount;
    private String customer_username;
}