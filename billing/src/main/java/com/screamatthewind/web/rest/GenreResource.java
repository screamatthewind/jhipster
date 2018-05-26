package com.screamatthewind.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.screamatthewind.domain.Genre;

import com.screamatthewind.repository.GenreRepository;
import com.screamatthewind.web.rest.errors.BadRequestAlertException;
import com.screamatthewind.web.rest.util.HeaderUtil;
import com.screamatthewind.service.dto.GenreDTO;
import com.screamatthewind.service.mapper.GenreMapper;
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
 * REST controller for managing Genre.
 */
@RestController
@RequestMapping("/api")
public class GenreResource {

    private final Logger log = LoggerFactory.getLogger(GenreResource.class);

    private static final String ENTITY_NAME = "genre";

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    public GenreResource(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    /**
     * POST  /genres : Create a new genre.
     *
     * @param genreDTO the genreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genreDTO, or with status 400 (Bad Request) if the genre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/genres")
    @Timed
    public ResponseEntity<GenreDTO> createGenre(@RequestBody GenreDTO genreDTO) throws URISyntaxException {
        log.debug("REST request to save Genre : {}", genreDTO);
        if (genreDTO.getId() != null) {
            throw new BadRequestAlertException("A new genre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Genre genre = genreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        GenreDTO result = genreMapper.toDto(genre);
        return ResponseEntity.created(new URI("/api/genres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genres : Updates an existing genre.
     *
     * @param genreDTO the genreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genreDTO,
     * or with status 400 (Bad Request) if the genreDTO is not valid,
     * or with status 500 (Internal Server Error) if the genreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/genres")
    @Timed
    public ResponseEntity<GenreDTO> updateGenre(@RequestBody GenreDTO genreDTO) throws URISyntaxException {
        log.debug("REST request to update Genre : {}", genreDTO);
        if (genreDTO.getId() == null) {
            return createGenre(genreDTO);
        }
        Genre genre = genreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        GenreDTO result = genreMapper.toDto(genre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genres : get all the genres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genres in body
     */
    @GetMapping("/genres")
    @Timed
    public List<GenreDTO> getAllGenres() {
        log.debug("REST request to get all Genres");
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.toDto(genres);
        }

    /**
     * GET  /genres/:id : get the "id" genre.
     *
     * @param id the id of the genreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/genres/{id}")
    @Timed
    public ResponseEntity<GenreDTO> getGenre(@PathVariable Long id) {
        log.debug("REST request to get Genre : {}", id);
        Genre genre = genreRepository.findOne(id);
        GenreDTO genreDTO = genreMapper.toDto(genre);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genreDTO));
    }

    /**
     * DELETE  /genres/:id : delete the "id" genre.
     *
     * @param id the id of the genreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/genres/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        log.debug("REST request to delete Genre : {}", id);
        genreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
