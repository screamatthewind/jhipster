package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.AgentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agent and its DTO AgentDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class})
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {

    @Mapping(source = "template", target = "templateDTO")
    AgentDTO toDto(Agent agent);

    @Mapping(source = "templateDTO", target = "template")
    Agent toEntity(AgentDTO agentDTO);

    default Agent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }
}
