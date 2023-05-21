package com.infogen.loyalty.exception;

import lombok.Getter;

import java.util.Map;

public class MissingOrBadParameterException extends RuntimeException {

    @Getter
    private Map<String, String> paramsValidation;

    public MissingOrBadParameterException(String message) {
        super(message);
    }

    public MissingOrBadParameterException(String message, Map<String, String> paramsValidation) {
        super(message);
        this.paramsValidation = paramsValidation;
    }
}