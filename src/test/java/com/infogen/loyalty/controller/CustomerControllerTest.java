package com.infogen.loyalty.controller;

import com.infogen.loyalty.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void getCustomersRewardedPoints_WhenRewardedPointsRetrieved_WillReturn200StatusCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewardedPoints"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCustomersRewardedPoints_WhenRewardedPointsNotFound_WillThrow404StatusCode() throws Exception {

        when(customerService.calculateCustomerRewardedPoints(anyInt(), anyInt())).
                thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewardedPoints"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}