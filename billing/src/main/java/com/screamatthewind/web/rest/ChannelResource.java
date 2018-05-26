package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.Channel;

import com.screamatthewind.repository.ChannelRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.web.rest.util.PaginationUtil;
import com.screamatthewind.service.dto.ChannelDTO;
import com.screamatthewind.service.mapper.ChannelMapper;
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
 * REST controller for managing Channel.
 */
@RestController
@RequestMapping("/api")
public class ChannelResource {

    private final Logger log = LoggerFactory.getLogger(ChannelResource.class);

    private static final String ENTITY_NAME = "channel";

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    public ChannelResource(ChannelRepository channelRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    /**
     * POST  /channels : Create a new channel.
     *
     * @param channelDTO the channelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new channelDTO, or with status 400 (Bad Request) if the channel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/channels")
    @Timed
    public ResponseEntity<ChannelDTO> createChannel(@Valid @RequestBody ChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to save Channel : {}", channelDTO);
        if (channelDTO.getId() != null) {
            throw new BadRequestAlertException("A new channel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        ChannelDTO result = channelMapper.toDto(channel);
        return ResponseEntity.created(new URI("/api/channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /channels : Updates an existing channel.
     *
     * @param channelDTO the channelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated channelDTO,
     * or with status 400 (Bad Request) if the channelDTO is not valid,
     * or with status 500 (Internal Server Error) if the channelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/channels")
    @Timed
    public ResponseEntity<ChannelDTO> updateChannel(@Valid @RequestBody ChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to update Channel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannel(channelDTO);
        }
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        ChannelDTO result = channelMapper.toDto(channel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, channelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /channels : get all the channels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of channels in body
     */
    @GetMapping("/channels")
    @Timed
    public ResponseEntity<List<ChannelDTO>> getAllChannels(Pageable pageable) {
        log.debug("REST request to get a page of Channels");
        Page<Channel> page = channelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/channels");
        return new ResponseEntity<>(channelMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /channels/:id : get the "id" channel.
     *
     * @param id the id of the channelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the channelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/channels/{id}")
    @Timed
    public ResponseEntity<ChannelDTO> getChannel(@PathVariable Long id) {
        log.debug("REST request to get Channel : {}", id);
        Channel channel = channelRepository.findOne(id);
        ChannelDTO channelDTO = channelMapper.toDto(channel);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(channelDTO));
    }

    /**
     * DELETE  /channels/:id : delete the "id" channel.
     *
     * @param id the id of the channelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/channels/{id}")
    @Timed
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        log.debug("REST request to delete Channel : {}", id);
        channelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
