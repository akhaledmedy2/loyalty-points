package com.infogen.loyalty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<CommonExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CommonExceptionResponse exceptionResponse = composeCommonExceptionResponse(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityPersistenceException.class)
    public ResponseEntity<CommonExceptionResponse> handleEntityPersistenceException(EntityPersistenceException ex) {
        CommonExceptionResponse exceptionResponse = composeCommonExceptionResponse(ex);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidTransactionStatusException.class)
    public ResponseEntity<CommonExceptionResponse> handleInvalidTransactionStatusException(InvalidTransactionStatusException ex) {
        CommonExceptionResponse exceptionResponse = composeCommonExceptionResponse(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingOrBadParameterException.class)
    public ResponseEntity<CommonExceptionResponse> handleMissingOrBadParameterException(MissingOrBadParameterException ex) {
        ParameterValidationResponse exceptionResponse = new ParameterValidationResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setParamsValidation(ex.getParamsValidation());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private CommonExceptionResponse composeCommonExceptionResponse(Exception ex) {
        CommonExceptionResponse exceptionResponse = new CommonExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTimestamp(new Date());
        return exceptionResponse;
    }
}