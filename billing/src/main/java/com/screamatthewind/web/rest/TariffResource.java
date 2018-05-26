package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.Tariff;

import com.screamatthewind.repository.TariffRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.service.dto.TariffDTO;
import com.screamatthewind.service.mapper.TariffMapper;
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
 * REST controller for managing Tariff.
 */
@RestController
@RequestMapping("/api")
public class TariffResource {

    private final Logger log = LoggerFactory.getLogger(TariffResource.class);

    private static final String ENTITY_NAME = "tariff";

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    public TariffResource(TariffRepository tariffRepository, TariffMapper tariffMapper) {
        this.tariffRepository = tariffRepository;
        this.tariffMapper = tariffMapper;
    }

    /**
     * POST  /tariffs : Create a new tariff.
     *
     * @param tariffDTO the tariffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tariffDTO, or with status 400 (Bad Request) if the tariff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tariffs")
    @Timed
    public ResponseEntity<TariffDTO> createTariff(@RequestBody TariffDTO tariffDTO) throws URISyntaxException {
        log.debug("REST request to save Tariff : {}", tariffDTO);
        if (tariffDTO.getId() != null) {
            throw new BadRequestAlertException("A new tariff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        TariffDTO result = tariffMapper.toDto(tariff);
        return ResponseEntity.created(new URI("/api/tariffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tariffs : Updates an existing tariff.
     *
     * @param tariffDTO the tariffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tariffDTO,
     * or with status 400 (Bad Request) if the tariffDTO is not valid,
     * or with status 500 (Internal Server Error) if the tariffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tariffs")
    @Timed
    public ResponseEntity<TariffDTO> updateTariff(@RequestBody TariffDTO tariffDTO) throws URISyntaxException {
        log.debug("REST request to update Tariff : {}", tariffDTO);
        if (tariffDTO.getId() == null) {
            return createTariff(tariffDTO);
        }
        Tariff tariff = tariffMapper.toEntity(tariffDTO);
        tariff = tariffRepository.save(tariff);
        TariffDTO result = tariffMapper.toDto(tariff);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tariffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tariffs : get all the tariffs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tariffs in body
     */
    @GetMapping("/tariffs")
    @Timed
    public List<TariffDTO> getAllTariffs() {
        log.debug("REST request to get all Tariffs");
        List<Tariff> tariffs = tariffRepository.findAll();
        return tariffMapper.toDto(tariffs);
        }

    /**
     * GET  /tariffs/:id : get the "id" tariff.
     *
     * @param id the id of the tariffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tariffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tariffs/{id}")
    @Timed
    public ResponseEntity<TariffDTO> getTariff(@PathVariable Long id) {
        log.debug("REST request to get Tariff : {}", id);
        Tariff tariff = tariffRepository.findOne(id);
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tariffDTO));
    }

    /**
     * DELETE  /tariffs/:id : delete the "id" tariff.
     *
     * @param id the id of the tariffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tariffs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTariff(@PathVariable Long id) {
        log.debug("REST request to delete Tariff : {}", id);
        tariffRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
