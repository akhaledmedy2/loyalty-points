package com.infogen.loyalty.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CustomerDto {
    private String name;
    private String username;
    private Date creation_date;

    @JsonIgnore
    private Date update_date;
    @JsonIgnore
    private boolean soft_deleted;

    @JsonIgnore
    private boolean enabled;
}