package com.screamatthewind.service.mapper;

import com.screamatthewind.domain.*;
import com.screamatthewind.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {



    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
