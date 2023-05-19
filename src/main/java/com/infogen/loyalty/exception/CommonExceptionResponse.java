package com.infogen.loyalty.exception;

import lombok.Data;

import java.util.Date;

@Data
public class CommonExceptionResponse {
    protected String message;
    protected Date timestamp;
}