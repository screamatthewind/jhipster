package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.Stb;

import com.screamatthewind.repository.StbRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.web.rest.util.PaginationUtil;
import com.screamatthewind.service.dto.StbDTO;
import com.screamatthewind.service.mapper.StbMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Stb.
 */
@RestController
@RequestMapping("/api")
public class StbResource {

    private final Logger log = LoggerFactory.getLogger(StbResource.class);

    private static final String ENTITY_NAME = "stb";

    private final StbRepository stbRepository;

    private final StbMapper stbMapper;

    public StbResource(StbRepository stbRepository, StbMapper stbMapper) {
        this.stbRepository = stbRepository;
        this.stbMapper = stbMapper;
    }

    /**
     * POST  /stbs : Create a new stb.
     *
     * @param stbDTO the stbDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stbDTO, or with status 400 (Bad Request) if the stb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stbs")
    @Timed
    public ResponseEntity<StbDTO> createStb(@Valid @RequestBody StbDTO stbDTO) throws URISyntaxException {
        log.debug("REST request to save Stb : {}", stbDTO);
        if (stbDTO.getId() != null) {
            throw new BadRequestAlertException("A new stb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stb stb = stbMapper.toEntity(stbDTO);
        stb = stbRepository.save(stb);
        StbDTO result = stbMapper.toDto(stb);
        return ResponseEntity.created(new URI("/api/stbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stbs : Updates an existing stb.
     *
     * @param stbDTO the stbDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stbDTO,
     * or with status 400 (Bad Request) if the stbDTO is not valid,
     * or with status 500 (Internal Server Error) if the stbDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stbs")
    @Timed
    public ResponseEntity<StbDTO> updateStb(@Valid @RequestBody StbDTO stbDTO) throws URISyntaxException {
        log.debug("REST request to update Stb : {}", stbDTO);
        if (stbDTO.getId() == null) {
            return createStb(stbDTO);
        }
        Stb stb = stbMapper.toEntity(stbDTO);
        stb = stbRepository.save(stb);
        StbDTO result = stbMapper.toDto(stb);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stbDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stbs : get all the stbs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stbs in body
     */
    @GetMapping("/stbs")
    @Timed
    public ResponseEntity<List<StbDTO>> getAllStbs(Pageable pageable) {
        log.debug("REST request to get a page of Stbs");
        Page<Stb> page = stbRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stbs");
        return new ResponseEntity<>(stbMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /stbs/:id : get the "id" stb.
     *
     * @param id the id of the stbDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stbDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stbs/{id}")
    @Timed
    public ResponseEntity<StbDTO> getStb(@PathVariable Long id) {
        log.debug("REST request to get Stb : {}", id);
        Stb stb = stbRepository.findOne(id);
        StbDTO stbDTO = stbMapper.toDto(stb);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stbDTO));
    }

    /**
     * DELETE  /stbs/:id : delete the "id" stb.
     *
     * @param id the id of the stbDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stbs/{id}")
    @Timed
    public ResponseEntity<Void> deleteStb(@PathVariable Long id) {
        log.debug("REST request to delete Stb : {}", id);
        stbRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
