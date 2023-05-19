package com.infogen.loyalty.service;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.entity.RewardPoints;
import com.infogen.loyalty.mapper.CustomerMapper;
import com.infogen.loyalty.payload.dto.CustomerDto;
import com.infogen.loyalty.payload.dto.RewardedPointsDto;
import com.infogen.loyalty.payload.response.CustomerPointsResponse;
import com.infogen.loyalty.repository.CustomerRepository;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private RewardPointsService rewardPointsService;

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    public Customer getCustomerByUsername(String username) {
        Optional<Customer> customerOptional = repository.findOneByUsername(username);
        if (!customerOptional.isPresent()) {
            logger.error("Customer not found against this username [{}]", username);
            throw new EntityNotFoundException("Customer not found against this username");
        }
        return customerOptional.get();
    }

    public Map<CustomerDto, CustomerPointsResponse> calculateCustomerRewardedPoints(int page, int size) {
        Map<Customer, List<RewardPoints>> rewardPointsMap = rewardPointsService.getPointsWithLastThreeMonths(
                PageRequest.of(page, size));

        Map<CustomerDto, CustomerPointsResponse> customersMap = new HashMap<>();

        CustomerDto customerDto;
        RewardedPointsDto rewardedPointsDto;
        List<RewardedPointsDto> rewardedPointsDtoList;
        CustomerPointsResponse customerPointsResponse;
        for (Map.Entry<Customer, List<RewardPoints>> rewardPoints : rewardPointsMap.entrySet()) {

            customerPointsResponse = new CustomerPointsResponse();
            rewardedPointsDtoList = new ArrayList<>();

            for (RewardPoints points : rewardPoints.getValue()) {
                rewardedPointsDto = new RewardedPointsDto();
                rewardedPointsDto.setRewarded_points(points.getPoints());
                rewardedPointsDto.setMonth(points.getMonth().toString());

                customerPointsResponse.setTotal_rewarded_points(customerPointsResponse.getTotal_rewarded_points()
                        + points.getPoints());

                rewardedPointsDtoList.add(rewardedPointsDto);
            }

            customerPointsResponse.setRewardedPoints(rewardedPointsDtoList);
            customerDto = mapper.mapToDto(rewardPoints.getKey());
            customersMap.put(customerDto, customerPointsResponse);

        }

        return customersMap;
    }
}