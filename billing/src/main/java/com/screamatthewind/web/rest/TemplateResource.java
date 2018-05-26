package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.Template;

import com.screamatthewind.repository.TemplateRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.service.dto.TemplateDTO;
import com.screamatthewind.service.mapper.TemplateMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "template";

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateResource(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * POST  /templates : Create a new template.
     *
     * @param templateDTO the templateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templateDTO, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    @Timed
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to save Template : {}", templateDTO);
        if (templateDTO.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        TemplateDTO result = templateMapper.toDto(template);
        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param templateDTO the templateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templateDTO,
     * or with status 400 (Bad Request) if the templateDTO is not valid,
     * or with status 500 (Internal Server Error) if the templateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    @Timed
    public ResponseEntity<TemplateDTO> updateTemplate(@RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to update Template : {}", templateDTO);
        if (templateDTO.getId() == null) {
            return createTemplate(templateDTO);
        }
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        TemplateDTO result = templateMapper.toDto(template);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    @Timed
    public List<TemplateDTO> getAllTemplates() {
        log.debug("REST request to get all Templates");
        List<Template> templates = templateRepository.findAll();
        return templateMapper.toDto(templates);
        }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the templateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    @Timed
    public ResponseEntity<TemplateDTO> getTemplate(@PathVariable Long id) {
        log.debug("REST request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        TemplateDTO templateDTO = templateMapper.toDto(template);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(templateDTO));
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the templateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        log.debug("REST request to delete Template : {}", id);
        templateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
