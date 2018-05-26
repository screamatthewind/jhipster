package com.therealdanvega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.therealdanvega.domain.JobHistory;

import com.therealdanvega.repository.JobHistoryRepository;
import com.therealdanvega.web.rest.errors.BadRequestAlertException;
import com.therealdanvega.web.rest.util.HeaderUtil;
import com.therealdanvega.web.rest.util.PaginationUtil;
import com.therealdanvega.service.dto.JobHistoryDTO;
import com.therealdanvega.service.mapper.JobHistoryMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobHistory.
 */
@RestController
@RequestMapping("/api")
public class JobHistoryResource {

    private final Logger log = LoggerFactory.getLogger(JobHistoryResource.class);

    private static final String ENTITY_NAME = "jobHistory";

    private final JobHistoryRepository jobHistoryRepository;

    private final JobHistoryMapper jobHistoryMapper;

    public JobHistoryResource(JobHistoryRepository jobHistoryRepository, JobHistoryMapper jobHistoryMapper) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistoryMapper = jobHistoryMapper;
    }

    /**
     * POST  /job-histories : Create a new jobHistory.
     *
     * @param jobHistoryDTO the jobHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobHistoryDTO, or with status 400 (Bad Request) if the jobHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-histories")
    @Timed
    public ResponseEntity<JobHistoryDTO> createJobHistory(@RequestBody JobHistoryDTO jobHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save JobHistory : {}", jobHistoryDTO);
        if (jobHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryDTO);
        jobHistory = jobHistoryRepository.save(jobHistory);
        JobHistoryDTO result = jobHistoryMapper.toDto(jobHistory);
        return ResponseEntity.created(new URI("/api/job-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-histories : Updates an existing jobHistory.
     *
     * @param jobHistoryDTO the jobHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobHistoryDTO,
     * or with status 400 (Bad Request) if the jobHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-histories")
    @Timed
    public ResponseEntity<JobHistoryDTO> updateJobHistory(@RequestBody JobHistoryDTO jobHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update JobHistory : {}", jobHistoryDTO);
        if (jobHistoryDTO.getId() == null) {
            return createJobHistory(jobHistoryDTO);
        }
        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryDTO);
        jobHistory = jobHistoryRepository.save(jobHistory);
        JobHistoryDTO result = jobHistoryMapper.toDto(jobHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-histories : get all the jobHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobHistories in body
     */
    @GetMapping("/job-histories")
    @Timed
    public ResponseEntity<List<JobHistoryDTO>> getAllJobHistories(Pageable pageable) {
        log.debug("REST request to get a page of JobHistories");
        Page<JobHistory> page = jobHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-histories");
        return new ResponseEntity<>(jobHistoryMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-histories/:id : get the "id" jobHistory.
     *
     * @param id the id of the jobHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-histories/{id}")
    @Timed
    public ResponseEntity<JobHistoryDTO> getJobHistory(@PathVariable Long id) {
        log.debug("REST request to get JobHistory : {}", id);
        JobHistory jobHistory = jobHistoryRepository.findOne(id);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobHistoryDTO));
    }

    /**
     * DELETE  /job-histories/:id : delete the "id" jobHistory.
     *
     * @param id the id of the jobHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobHistory(@PathVariable Long id) {
        log.debug("REST request to delete JobHistory : {}", id);
        jobHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
