package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.PeriodType;

import com.screamatthewind.repository.PeriodTypeRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.service.dto.PeriodTypeDTO;
import com.screamatthewind.service.mapper.PeriodTypeMapper;
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
 * REST controller for managing PeriodType.
 */
@RestController
@RequestMapping("/api")
public class PeriodTypeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodTypeResource.class);

    private static final String ENTITY_NAME = "periodType";

    private final PeriodTypeRepository periodTypeRepository;

    private final PeriodTypeMapper periodTypeMapper;

    public PeriodTypeResource(PeriodTypeRepository periodTypeRepository, PeriodTypeMapper periodTypeMapper) {
        this.periodTypeRepository = periodTypeRepository;
        this.periodTypeMapper = periodTypeMapper;
    }

    /**
     * POST  /period-types : Create a new periodType.
     *
     * @param periodTypeDTO the periodTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodTypeDTO, or with status 400 (Bad Request) if the periodType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/period-types")
    @Timed
    public ResponseEntity<PeriodTypeDTO> createPeriodType(@RequestBody PeriodTypeDTO periodTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PeriodType : {}", periodTypeDTO);
        if (periodTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodType periodType = periodTypeMapper.toEntity(periodTypeDTO);
        periodType = periodTypeRepository.save(periodType);
        PeriodTypeDTO result = periodTypeMapper.toDto(periodType);
        return ResponseEntity.created(new URI("/api/period-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /period-types : Updates an existing periodType.
     *
     * @param periodTypeDTO the periodTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodTypeDTO,
     * or with status 400 (Bad Request) if the periodTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/period-types")
    @Timed
    public ResponseEntity<PeriodTypeDTO> updatePeriodType(@RequestBody PeriodTypeDTO periodTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PeriodType : {}", periodTypeDTO);
        if (periodTypeDTO.getId() == null) {
            return createPeriodType(periodTypeDTO);
        }
        PeriodType periodType = periodTypeMapper.toEntity(periodTypeDTO);
        periodType = periodTypeRepository.save(periodType);
        PeriodTypeDTO result = periodTypeMapper.toDto(periodType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /period-types : get all the periodTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodTypes in body
     */
    @GetMapping("/period-types")
    @Timed
    public List<PeriodTypeDTO> getAllPeriodTypes() {
        log.debug("REST request to get all PeriodTypes");
        List<PeriodType> periodTypes = periodTypeRepository.findAll();
        return periodTypeMapper.toDto(periodTypes);
        }

    /**
     * GET  /period-types/:id : get the "id" periodType.
     *
     * @param id the id of the periodTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/period-types/{id}")
    @Timed
    public ResponseEntity<PeriodTypeDTO> getPeriodType(@PathVariable Long id) {
        log.debug("REST request to get PeriodType : {}", id);
        PeriodType periodType = periodTypeRepository.findOne(id);
        PeriodTypeDTO periodTypeDTO = periodTypeMapper.toDto(periodType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(periodTypeDTO));
    }

    /**
     * DELETE  /period-types/:id : delete the "id" periodType.
     *
     * @param id the id of the periodTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/period-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodType(@PathVariable Long id) {
        log.debug("REST request to delete PeriodType : {}", id);
        periodTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
