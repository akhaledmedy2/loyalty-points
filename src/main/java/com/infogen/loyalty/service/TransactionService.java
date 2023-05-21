package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.Transaction;
import com.infogen.loyalty.enums.TransactionStatus;
import com.infogen.loyalty.exception.EntityPersistenceException;
import com.infogen.loyalty.exception.InvalidTransactionStatusException;
import com.infogen.loyalty.exception.MissingOrBadParameterException;
import com.infogen.loyalty.mapper.TransactionMapper;
import com.infogen.loyalty.payload.request.TransactionRequest;
import com.infogen.loyalty.payload.request.TransactionUpdateRequest;
import com.infogen.loyalty.payload.response.TransactionResponse;
import com.infogen.loyalty.repository.TransactionRepository;
import com.infogen.loyalty.logic.RewardPointsCalculator;
import com.infogen.loyalty.validators.ParamsValidator;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    private TransactionRepository repository;

    private CustomerService customerService;

    private RewardPointsService rewardPointsService;

    public TransactionService(TransactionRepository repository, CustomerService customerService, RewardPointsService rewardPointsService) {
        this.repository = repository;
        this.customerService = customerService;
        this.rewardPointsService = rewardPointsService;
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        Map<String, String> paramsValidation = ParamsValidator.validateRequest(transactionRequest);

        if (!paramsValidation.isEmpty()) {
            logger.error("Invalid request parameters [{}]", paramsValidation);
            throw new MissingOrBadParameterException("Invalid request parameters", paramsValidation);
        }

        Customer customer = customerService.getCustomerByUsername(transactionRequest.getCustomer_username());

        Transaction transaction = createTransactionDetails(transactionRequest, customer);

        saveTransaction(transaction);

        return mapper.mapToResponse(transaction);
    }

    public TransactionResponse updateTransaction(TransactionUpdateRequest transactionUpdateRequest) {
        String transactionStatus;
        try {
            transactionStatus = TransactionStatus.values()[transactionUpdateRequest.getTransaction_status()].name();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Invalid transaction status for transaction Id [{}]", transactionUpdateRequest.getTransaction_id());
            throw new InvalidTransactionStatusException("Transaction status invalid against enum");
        }

        Transaction transaction = getTransactionByTransactionId(transactionUpdateRequest.getTransaction_id());

        int rewardedPoints = RewardPointsCalculator.calculate(transaction.getAmount());
        transaction.setLoyaltyPoints(rewardedPoints);
        transaction.setStatus(transactionStatus);
        transaction.setAmount(transactionUpdateRequest.getAmount());
        transaction.setUpdateDate(new Date());

        saveTransaction(transaction);

        createOrAdjustRewardPoints(transaction.getCustomer(), transaction.getUpdateDate(), rewardedPoints);

        return mapper.mapToResponse(transaction);
    }

    public Transaction getTransactionByTransactionId(String transactionId) {
        Optional<Transaction> transactionOptional = repository.findOneByTransactionId(transactionId);
        if (!transactionOptional.isPresent()) {
            logger.error("Transaction not found for id [{}]", transactionId);
            throw new EntityNotFoundException("Transaction not found");
        }

        return transactionOptional.get();
    }

    @Async
    private void createOrAdjustRewardPoints(Customer customer, Date transactionDate, int rewardedPoints) {
        rewardPointsService.createRewardPoints(customer, transactionDate, rewardedPoints);
    }

    private Transaction createTransactionDetails(TransactionRequest transactionRequest, Customer customer) {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setCreationDate(new Date());
        transaction.setUpdateDate(new Date());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus(TransactionStatus.PENDING_PAYMENT.name());
        transaction.setTransactionId(UUID.randomUUID().toString().replace("-", ""));
        return transaction;
    }

    private void saveTransaction(Transaction transaction) {
        try {
            repository.save(transaction);
            logger.info("Transaction [{}] saved for customer [{}] with status [{}]", transaction.getTransactionId(),
                    transaction.getCustomer().getUsername(), transaction.getStatus());
        } catch (Exception e) {
            logger.info("cannot save transaction entity with data for customer [{}]",transaction.getCustomer().getUsername());
            throw new EntityPersistenceException("cannot save transaction entity");
        }
    }
}