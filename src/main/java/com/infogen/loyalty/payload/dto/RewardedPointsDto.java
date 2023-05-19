package com.infogen.loyalty.payload.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RewardedPointsDto {
    private String month;
    private int rewarded_points;
}