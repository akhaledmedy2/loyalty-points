package com.infogen.loyalty.mapper;

import com.infogen.loyalty.entity.Transaction;
import com.infogen.loyalty.payload.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface TransactionMapper {
    @Mappings({
            @Mapping(target = "transaction_id",source = "transactionId"),
            @Mapping(target = "loyalty_points",source = "loyaltyPoints"),
            @Mapping(target = "transaction_status",source = "status"),
            @Mapping(target = "creation_date",source = "creationDate"),
            @Mapping(target = "update_date",source = "updateDate"),
            @Mapping(target = "customer.creation_date",source = "customer.creationDate")
    })
    TransactionResponse mapToResponse(Transaction entity);
}