package com.infogen.loyalty.payload.response;

import com.infogen.loyalty.payload.dto.RewardedPointsDto;
import lombok.Data;

import java.util.List;

@Data
public class CustomerPointsResponse {
    private List<RewardedPointsDto> rewardedPoints;
    private int total_rewarded_points;
}