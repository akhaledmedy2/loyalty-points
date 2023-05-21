package com.infogen.loyalty.mapper;

import com.infogen.loyalty.entity.Customer;
import com.infogen.loyalty.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto mapToDto(Customer entity);
}