package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import com.infogen.loyalty.exception.EntityPersistenceException;
import com.infogen.loyalty.repository.RewardPointsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardPointsServiceTest {
    @Mock
    private Logger logger;
    @Mock
    private RewardPointsRepository repository;
    @InjectMocks
    private RewardPointsService rewardPointsService;

    private List<RewardPoints> rewardPointsList;

    private Optional<RewardPoints> rewardPointsOptional;

    private RewardPoints rewardPoints;

    private Customer customer;

    @BeforeEach
    public void init() {
        customer = new Customer();
        customer.setId(1);
        customer.setUsername("testname");
        customer.setName("name");
        customer.setCreationDate(new Date());
        customer.setUpdateDate(new Date());

        rewardPointsList = new ArrayList<>();

        rewardPoints = new RewardPoints();
        rewardPoints.setId(1);
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(new Date());
        rewardPoints.setPoints(350 * 2);
        rewardPointsList.add(rewardPoints);

        rewardPoints = new RewardPoints();
        rewardPoints.setId(2);
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth(new Date());
        rewardPoints.setPoints(350 * 2);
        rewardPointsList.add(rewardPoints);
    }

    @Test
    public void createRewardPoints_WhenRewardPointsFound_WillUpdateRewardPointsEntity() {
        when(repository.findOneByCustomerAndMonth(any(Customer.class), any(Date.class)))
                .thenReturn(Optional.ofNullable(rewardPoints));

        when(repository.save(any(RewardPoints.class))).thenReturn(rewardPoints);

        rewardPointsService.createRewardPoints(customer, new Date(), 50);

        assertEquals(rewardPoints.getPoints(), 750);
    }

    @Test
    public void createRewardPoints_WhenRewardPointsNotFound_WillCreateRewardPointsEntity() {
        when(repository.findOneByCustomerAndMonth(any(Customer.class), any(Date.class)))
                .thenReturn(Optional.empty());

        rewardPoints.setPoints(50);
        when(repository.save(any(RewardPoints.class))).thenReturn(rewardPoints);

        rewardPointsService.createRewardPoints(customer, new Date(), 50);

        assertEquals(rewardPoints.getPoints(), 50);
    }

    @Test
    public void getPointsWithLastThreeMonths_WhenRewardPointsListRetrieved_WillReturnCustomerMapResponse() {
        when(repository.findAllByMonthBetweenAndGroupByCustomer(any(Date.class), any(Date.class),
                any(Pageable.class))).thenReturn(rewardPointsList);

        Map<Customer, List<RewardPoints>> customerMapResponse = rewardPointsService
                .getPointsWithLastThreeMonths(PageRequest.of(0, 10));

        assertEquals(1, customerMapResponse.size());
    }

    @Test
    public void getPointsWithLastThreeMonths_WhenNoRecordsFound_WillThrowEntityNotFoundException() {
        when(repository.findAllByMonthBetweenAndGroupByCustomer(any(Date.class), any(Date.class),
                any(Pageable.class))).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> {
            rewardPointsService.getPointsWithLastThreeMonths(PageRequest.of(0, 10));
        });
    }

    @Test
    public void createRewardPoints_WhenRewardPointsNotSaved_WillThrowEntityPersistenceException() {
        when(repository.save(any(RewardPoints.class))).thenThrow(new EntityPersistenceException("message"));

        assertThrows(EntityPersistenceException.class, () -> {
            rewardPointsService.createRewardPoints(customer, new Date(), 50);
        });
    }
}