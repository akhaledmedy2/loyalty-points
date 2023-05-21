package com.infogen.loyalty.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infogen.loyalty.exception.InvalidTransactionStatusException;
import com.infogen.loyalty.exception.MissingOrBadParameterException;
import com.infogen.loyalty.model.request.TransactionRequest;
import com.infogen.loyalty.model.request.TransactionUpdateRequest;
import com.infogen.loyalty.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private static String asJsonString(final Object requestBody) {
        try {
            return new ObjectMapper().writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createTransaction_WhenTransactionCreated_WillReturn200StatusCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .content(asJsonString(initiateTransactionRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createTransaction_WhenRequestBodyIsInvalid_WillReturn400StatusCode() throws Exception {

        when(transactionService.createTransaction(any(TransactionRequest.class)))
                .thenThrow(new MissingOrBadParameterException(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .content(asJsonString(initiateTransactionRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateTransaction_WhenTransactionFound_WillReturn200StatusCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/transaction")
                        .content(asJsonString(initiateTransactionUpdateRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTransaction_WhenInvalidTransactionStatus_WillReturn400StatusCode() throws Exception {
        when(transactionService.updateTransaction(any(TransactionUpdateRequest.class)))
                .thenThrow(new InvalidTransactionStatusException(""));

        mockMvc.perform(MockMvcRequestBuilders.put("/transaction")
                        .content(asJsonString(initiateTransactionUpdateRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateTransaction_WhenTransactionNotFound_WillReturn404StatusCode() throws Exception {
        when(transactionService.updateTransaction(any(TransactionUpdateRequest.class)))
                .thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders.put("/transaction")
                        .content(asJsonString(initiateTransactionUpdateRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private TransactionUpdateRequest initiateTransactionUpdateRequest() {
        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setTransaction_status(0);
        transactionUpdateRequest.setTransaction_id("892374897389127389");
        transactionUpdateRequest.setAmount(50);
        return transactionUpdateRequest;
    }

    private TransactionRequest initiateTransactionRequest() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCustomer_username("test");
        transactionRequest.setAmount(100);
        return transactionRequest;
    }
}