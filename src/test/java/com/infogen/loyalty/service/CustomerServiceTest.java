package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import com.infogen.loyalty.entity.Transaction;
import com.infogen.loyalty.enums.TransactionStatus;
import com.infogen.loyalty.dto.CustomerDto;
import com.infogen.loyalty.model.response.CustomerPointsResponse;
import com.infogen.loyalty.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RewardPointsService rewardPointsService;
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private Logger logger;
    private Customer customer;
    private Optional<Customer> customerOptional;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private RewardPoints rewardPoints;
    List<RewardPoints> rewardPointsList;

    @BeforeAll
    void init() {
        customer = new Customer();
        customer.setId(1);
        customer.setUsername("testname");
        customer.setName("name");
        customer.setCreationDate(new Date());
        customer.setUpdateDate(new Date());

        transactionList = new ArrayList<>();

        transaction = new Transaction();
        transaction.setAmount(120);
        transaction.setUpdateDate(new Date());
        transaction.setCreationDate(new Date());
        transaction.setLoyaltyPoints(90);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        transaction = new Transaction();
        transaction.setAmount(50);
        transaction.setUpdateDate(new Date());
        transaction.setCreationDate(new Date());
        transaction.setLoyaltyPoints(50);
        transaction.setStatus(TransactionStatus.PAID.name());
        transaction.setCustomer(customer);
        transactionList.add(transaction);

        rewardPointsList = new ArrayList<>();

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(new Date());
        rewardPoints.setPoints(350 * 2);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(new Date());
        rewardPoints.setPoints(50);
        rewardPointsList.add(rewardPoints);

        customer.setTransactions(transactionList);
        customer.setRewardPoints(rewardPointsList);
    }

    @Test
    void getCustomerByUsername_WhenCustomerFound_WillReturnCustomerEntity() {
        customerOptional = Optional.ofNullable(customer);
        when(customerRepository.findOneByUsername(anyString())).thenReturn(customerOptional);
        Customer customer = customerService.getCustomerByUsername("username");
        assertTrue(customer.getId() > 0);
    }

    @Test
    void getCustomerByUsername_WhenCustomerNotFound_WillThrowEntityNotFoundException() {
        when(customerRepository.findOneByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomerByUsername("username");
        });
    }

    @Test
    void calculateCustomerRewardedPoints_WhenRewardPointsRetrieved_WillReturnRequiredData() {
        Map<Customer, List<RewardPoints>> rewardPointsMap = new HashMap<>();
        rewardPointsMap.put(customer, rewardPointsList);
        when(rewardPointsService.getPointsWithLastThreeMonths(any(Pageable.class))).thenReturn(rewardPointsMap);

        Map<CustomerDto, CustomerPointsResponse> customerMapResponse =
                customerService.calculateCustomerRewardedPoints(0, 10);

        assertFalse(customerMapResponse.isEmpty());
    }
}