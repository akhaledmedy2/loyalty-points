package com.infogen.loyalty.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardPointsCalculatorTest {

    @Test
    public void calculate_WhenAmountLessThan100AndLessThan50_NoPointsRewarded() {
        int rewardPoints = RewardPointsCalculator.calculate(49);
        assertEquals(rewardPoints,0);
    }

    @Test
    public void calculate_WhenAmountLessThan100AndGreaterThan50_RewardedPointsIs1() {
        int rewardPoints = RewardPointsCalculator.calculate(51);
        assertEquals(rewardPoints,1);
    }

    @Test
    public void calculate_WhenAmountGreaterThan100AndGreaterThan50_RewardedPointsIs1Over50And2Over100() {
        int rewardPoints = RewardPointsCalculator.calculate(120);
        assertEquals(rewardPoints,90);
    }
}