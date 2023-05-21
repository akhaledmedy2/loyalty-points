package com.infogen.loyalty.model.response;

import com.infogen.loyalty.dto.RewardedPointsDto;
import lombok.Data;

import java.util.List;

@Data
public class CustomerPointsResponse {
    private List<RewardedPointsDto> rewardedPoints;
    private int total_rewarded_points;
}