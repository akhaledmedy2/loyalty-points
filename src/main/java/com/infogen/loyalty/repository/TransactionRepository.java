package com.infogen.loyalty.repository;

import com.infogen.loyalty.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findOneByTransactionId(String transactionId);
}