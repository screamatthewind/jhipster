package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "customer.id", target = "customerId")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "customerId", target = "customer")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
