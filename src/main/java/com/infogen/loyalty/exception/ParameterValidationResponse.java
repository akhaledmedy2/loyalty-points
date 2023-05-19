package com.infogen.loyalty.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParameterValidationResponse extends CommonExceptionResponse {
    Map<String, String> paramsValidation;

}