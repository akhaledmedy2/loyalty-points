package com.infogen.loyalty.controller;

import com.infogen.loyalty.model.request.TransactionRequest;
import com.infogen.loyalty.model.request.TransactionUpdateRequest;
import com.infogen.loyalty.model.response.TransactionResponse;
import com.infogen.loyalty.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Tag(name = "transaction", description = "transaction endpoints")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest createRequest) {
        return new ResponseEntity<>(transactionService.createTransaction(createRequest), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionUpdateRequest updateRequest) {
        return new ResponseEntity<>(transactionService.updateTransaction(updateRequest), HttpStatus.OK);
    }
}