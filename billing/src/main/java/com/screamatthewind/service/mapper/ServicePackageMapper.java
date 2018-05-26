package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.ServicePackageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServicePackage and its DTO ServicePackageDTO.
 */
@Mapper(componentModel = "spring", uses = {ChannelMapper.class})
public interface ServicePackageMapper extends EntityMapper<ServicePackageDTO, ServicePackage> {

    @Mapping(source = "channel.id", target = "channelId")
    ServicePackageDTO toDto(ServicePackage servicePackage);

    @Mapping(source = "channelId", target = "channel")
    @Mapping(target = "tariffs", ignore = true)
    ServicePackage toEntity(ServicePackageDTO servicePackageDTO);

    default ServicePackage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setId(id);
        return servicePackage;
    }
}
