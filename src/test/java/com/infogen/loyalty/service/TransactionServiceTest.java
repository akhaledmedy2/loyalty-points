package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.Transaction;
import com.infogen.loyalty.enums.TransactionStatus;
import com.infogen.loyalty.exception.EntityPersistenceException;
import com.infogen.loyalty.exception.InvalidTransactionStatusException;
import com.infogen.loyalty.exception.MissingOrBadParameterException;
import com.infogen.loyalty.model.request.TransactionRequest;
import com.infogen.loyalty.model.request.TransactionUpdateRequest;
import com.infogen.loyalty.model.response.TransactionResponse;
import com.infogen.loyalty.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private Logger logger;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private RewardPointsService rewardPointsService;
    @InjectMocks
    private TransactionService transactionService;

    private Customer customer;

    private Transaction transaction;

    private TransactionRequest transactionRequest;

    private TransactionUpdateRequest transactionUpdateRequest;

    @BeforeAll
    void init() {
        customer = new Customer();
        customer.setId(1);
        customer.setUsername("testname");
        customer.setName("name");
        customer.setCreationDate(new Date());
        customer.setUpdateDate(new Date());

        transaction = new Transaction();
        transaction.setId(1);
        transaction.setCustomer(customer);
        transaction.setCreationDate(new Date());
        transaction.setUpdateDate(new Date());
        transaction.setAmount(100);
        transaction.setStatus(TransactionStatus.PENDING_PAYMENT.name());
        transaction.setTransactionId("jkljaskldj89457348jasd");
        transaction.setLoyaltyPoints(50);

        transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(100);
        transactionRequest.setCustomer_username("test");

        transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setTransaction_id("jkljaskldj89457348jasd");
        transactionUpdateRequest.setAmount(150);
        transactionUpdateRequest.setTransaction_status(0);
    }

    @Test
    void createTransaction_WhenTransactionCreated_WillReturnTransactionResponse() {
        when(customerService.getCustomerByUsername(anyString())).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        assertNotNull(transactionResponse.getTransaction_id());
    }

    @Test
    void createTransaction_WhenTransactionNotSaved_WillThrowEntityPersistenceException() {
        when(customerService.getCustomerByUsername(anyString())).thenReturn(customer);

        when(transactionRepository.save(any(Transaction.class))).thenThrow(new EntityPersistenceException("message"));

        assertThrows(EntityPersistenceException.class, () -> {
            transactionService.createTransaction(transactionRequest);
        });
    }

    @Test
    void createTransaction_WhenTransactionRequestNotValid_WillThrowMissingOrBadParameterException() {
        transactionRequest = new TransactionRequest();

        assertThrows(MissingOrBadParameterException.class, () -> {
            transactionService.createTransaction(transactionRequest);
        });
    }

    @Test
    void updateTransaction_WhenTransactionUpdated_WillReturnTransactionResponse() {
        when(transactionRepository.findOneByTransactionId(anyString())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        TransactionResponse transactionResponse = transactionService.updateTransaction(transactionUpdateRequest);

        assertNotNull(transactionResponse.getTransaction_id());
    }

    @Test
    void updateTransaction_WhenTransactionStatusIsInvalid_WillThrowInvalidTransactionStatusException() {
        transactionUpdateRequest.setTransaction_status(5);

        assertThrows(InvalidTransactionStatusException.class, () -> {
            transactionService.updateTransaction(transactionUpdateRequest);
        });
    }

    @Test
    void getTransactionByTransactionId_WhenTransactionFound_WillReturnTransactionEntity() {
        when(transactionRepository.findOneByTransactionId(anyString())).thenReturn(Optional.of(transaction));

        Transaction transaction = transactionService.getTransactionByTransactionId("12312453");

        assertNotNull(transaction.getTransactionId());
    }

    @Test
    void getTransactionByTransactionId_WhenTransactionNotFound_WillThrowEntityNotFoundException() {
        when(transactionRepository.findOneByTransactionId(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            transactionService.getTransactionByTransactionId("12312453");
        });
    }
}