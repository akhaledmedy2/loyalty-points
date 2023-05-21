package com.infogen.loyalty.controller;

import com.infogen.loyalty.dto.CustomerDto;
import com.infogen.loyalty.model.response.CustomerPointsResponse;
import com.infogen.loyalty.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customer")
@Tag(name = "customer", description = "customer endpoints")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/rewardedPoints")
    public ResponseEntity<Map<CustomerDto, CustomerPointsResponse>> getCustomersRewardedPoints(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(customerService.calculateCustomerRewardedPoints(page, size), HttpStatus.OK);
    }
}