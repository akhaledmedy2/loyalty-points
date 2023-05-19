package com.infogen.loyalty.controller;

import com.infogen.loyalty.payload.request.TransactionRequest;
import com.infogen.loyalty.payload.request.TransactionUpdateRequest;
import com.infogen.loyalty.payload.response.TransactionResponse;
import com.infogen.loyalty.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Tag(name="transaction", description = "transaction endpoints")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(transactionService.createTransaction(transactionRequest), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionUpdateRequest updateRequest){
        return new ResponseEntity<>(transactionService.updateTransaction(updateRequest), HttpStatus.OK);
    }
}