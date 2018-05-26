package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.TariffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tariff and its DTO TariffDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodTypeMapper.class, ServicePackageMapper.class})
public interface TariffMapper extends EntityMapper<TariffDTO, Tariff> {

    @Mapping(source = "periodType.id", target = "periodTypeId")
    @Mapping(source = "servicePackage.id", target = "servicePackageId")
    TariffDTO toDto(Tariff tariff);

    @Mapping(source = "periodTypeId", target = "periodType")
    @Mapping(source = "servicePackageId", target = "servicePackage")
    Tariff toEntity(TariffDTO tariffDTO);

    default Tariff fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tariff tariff = new Tariff();
        tariff.setId(id);
        return tariff;
    }
}
