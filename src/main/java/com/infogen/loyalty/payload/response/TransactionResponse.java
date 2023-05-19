package com.infogen.loyalty.payload.response;

import com.infogen.loyalty.payload.dto.CustomerDto;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionResponse {
    private double amount;
    private Date creation_date;
    private Date update_date;
    private int loyalty_points;
    private String transaction_id;
    private String transaction_status;
    private CustomerDto customer;
}