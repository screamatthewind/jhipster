package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.StbDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stb and its DTO StbDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, TariffMapper.class})
public interface StbMapper extends EntityMapper<StbDTO, Stb> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "tariff.id", target = "tariffId")
    StbDTO toDto(Stb stb);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "tariffId", target = "tariff")
    Stb toEntity(StbDTO stbDTO);

    default Stb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stb stb = new Stb();
        stb.setId(id);
        return stb;
    }
}
