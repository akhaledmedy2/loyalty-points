package com.infogen.loyalty.calculator;

public class RewardPointsCalculator {

    public static int calculate(double amount) {

        int rewardPoints = 0;

        if (amount > 100) {
            rewardPoints += (int) ((amount - 100) * 2);
        }

        if (amount > 50) {
            rewardPoints += Math.min(amount, 100) - 50;
        }

        return rewardPoints;
    }
}