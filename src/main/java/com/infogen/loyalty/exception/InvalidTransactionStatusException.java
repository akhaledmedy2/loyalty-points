package com.infogen.loyalty.exception;

public class InvalidTransactionStatusException extends RuntimeException {

    public InvalidTransactionStatusException(String message) {
        super(message);
    }
}