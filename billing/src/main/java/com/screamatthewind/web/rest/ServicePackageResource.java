package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.ServicePackage;

import com.screamatthewind.repository.ServicePackageRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.service.dto.ServicePackageDTO;
import com.screamatthewind.service.mapper.ServicePackageMapper;
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
 * REST controller for managing ServicePackage.
 */
@RestController
@RequestMapping("/api")
public class ServicePackageResource {

    private final Logger log = LoggerFactory.getLogger(ServicePackageResource.class);

    private static final String ENTITY_NAME = "servicePackage";

    private final ServicePackageRepository servicePackageRepository;

    private final ServicePackageMapper servicePackageMapper;

    public ServicePackageResource(ServicePackageRepository servicePackageRepository, ServicePackageMapper servicePackageMapper) {
        this.servicePackageRepository = servicePackageRepository;
        this.servicePackageMapper = servicePackageMapper;
    }

    /**
     * POST  /service-packages : Create a new servicePackage.
     *
     * @param servicePackageDTO the servicePackageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicePackageDTO, or with status 400 (Bad Request) if the servicePackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackageDTO> createServicePackage(@RequestBody ServicePackageDTO servicePackageDTO) throws URISyntaxException {
        log.debug("REST request to save ServicePackage : {}", servicePackageDTO);
        if (servicePackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new servicePackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicePackage servicePackage = servicePackageMapper.toEntity(servicePackageDTO);
        servicePackage = servicePackageRepository.save(servicePackage);
        ServicePackageDTO result = servicePackageMapper.toDto(servicePackage);
        return ResponseEntity.created(new URI("/api/service-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-packages : Updates an existing servicePackage.
     *
     * @param servicePackageDTO the servicePackageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicePackageDTO,
     * or with status 400 (Bad Request) if the servicePackageDTO is not valid,
     * or with status 500 (Internal Server Error) if the servicePackageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackageDTO> updateServicePackage(@RequestBody ServicePackageDTO servicePackageDTO) throws URISyntaxException {
        log.debug("REST request to update ServicePackage : {}", servicePackageDTO);
        if (servicePackageDTO.getId() == null) {
            return createServicePackage(servicePackageDTO);
        }
        ServicePackage servicePackage = servicePackageMapper.toEntity(servicePackageDTO);
        servicePackage = servicePackageRepository.save(servicePackage);
        ServicePackageDTO result = servicePackageMapper.toDto(servicePackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicePackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-packages : get all the servicePackages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servicePackages in body
     */
    @GetMapping("/service-packages")
    @Timed
    public List<ServicePackageDTO> getAllServicePackages() {
        log.debug("REST request to get all ServicePackages");
        List<ServicePackage> servicePackages = servicePackageRepository.findAll();
        return servicePackageMapper.toDto(servicePackages);
        }

    /**
     * GET  /service-packages/:id : get the "id" servicePackage.
     *
     * @param id the id of the servicePackageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicePackageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-packages/{id}")
    @Timed
    public ResponseEntity<ServicePackageDTO> getServicePackage(@PathVariable Long id) {
        log.debug("REST request to get ServicePackage : {}", id);
        ServicePackage servicePackage = servicePackageRepository.findOne(id);
        ServicePackageDTO servicePackageDTO = servicePackageMapper.toDto(servicePackage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePackageDTO));
    }

    /**
     * DELETE  /service-packages/:id : delete the "id" servicePackage.
     *
     * @param id the id of the servicePackageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicePackage(@PathVariable Long id) {
        log.debug("REST request to delete ServicePackage : {}", id);
        servicePackageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
