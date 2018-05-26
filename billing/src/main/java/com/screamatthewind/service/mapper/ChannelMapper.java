package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.ChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Channel and its DTO ChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {

    @Mapping(source = "genre.id", target = "genreId")
    ChannelDTO toDto(Channel channel);

    @Mapping(target = "servicePackages", ignore = true)
    @Mapping(source = "genreId", target = "genre")
    Channel toEntity(ChannelDTO channelDTO);

    default Channel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Channel channel = new Channel();
        channel.setId(id);
        return channel;
    }
}
