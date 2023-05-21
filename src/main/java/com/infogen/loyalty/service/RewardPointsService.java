package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import com.infogen.loyalty.exception.EntityPersistenceException;
import com.infogen.loyalty.repository.RewardPointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class RewardPointsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RewardPointsRepository repository;

    public RewardPointsService(RewardPointsRepository repository) {
        this.repository = repository;
    }

    public void createRewardPoints(Customer customer, Date month, int points) {
        Optional<RewardPoints> rewardPointsOptional = repository.findOneByCustomerAndMonth(customer, month);

        RewardPoints rewardPoints;
        if (rewardPointsOptional.isPresent()) {
            rewardPoints = rewardPointsOptional.get();
            rewardPoints.setPoints(points + rewardPoints.getPoints());
        } else {
            rewardPoints = new RewardPoints();
            rewardPoints.setCustomer(customer);
            rewardPoints.setMonth(month);
            rewardPoints.setPoints(points);
        }

        saveRewardPoints(rewardPoints);
    }

    public Map<Customer, List<RewardPoints>> getPointsWithLastThreeMonths(Pageable pageable) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        List<RewardPoints> rewardPoints = repository.findAllByMonthBetweenAndGroupByCustomer(calendar.getTime(),
                new Date(), pageable);
        if (!rewardPoints.isEmpty()) {
            Map<Customer, List<RewardPoints>> customerRewardPoints = new HashMap<>();

            List<RewardPoints> customerPointsList;
            Customer customer;
            for (RewardPoints points : rewardPoints) {
                customer = points.getCustomer();
                if (customerRewardPoints.containsKey(customer)) {
                    customerPointsList = customerRewardPoints.get(customer);
                } else {
                    customerPointsList = new ArrayList<>();
                }
                customerPointsList.add(points);
                customerRewardPoints.put(customer, customerPointsList);
            }
            return customerRewardPoints;
        }

        logger.info("No reward points records found");
        throw new EntityNotFoundException("No reward points records found");
    }

    private void saveRewardPoints(RewardPoints rewardPoints) {
        try {
            repository.save(rewardPoints);
            logger.info("Reward points of [{}] saved for customer [{}] and month [{}]", rewardPoints.getPoints(),
                    rewardPoints.getCustomer().getUsername(), rewardPoints.getMonth());
        } catch (Exception e) {
            logger.error("cannot save reward points entity for customer [{}]", rewardPoints.getCustomer().getUsername());
            throw new EntityPersistenceException("cannot save reward points entity");
        }
    }
}