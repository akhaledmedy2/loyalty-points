package com.infogen.loyalty.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardPointsCalculatorTest {

    @Test
    void calculate_WhenAmountLessThan100AndLessThan50_NoPointsRewarded() {
        int rewardPoints = RewardPointsCalculator.calculate(49);
        assertEquals(rewardPoints, 0);
    }

    @Test
    void calculate_WhenAmountLessThan100AndGreaterThan50_RewardedPointsIs1() {
        int rewardPoints = RewardPointsCalculator.calculate(51);
        assertEquals(rewardPoints, 1);
    }

    @Test
    void calculate_WhenAmountGreaterThan100AndGreaterThan50_RewardedPointsIs1Over50And2Over100() {
        int rewardPoints = RewardPointsCalculator.calculate(120);
        assertEquals(rewardPoints, 90);
    }
}