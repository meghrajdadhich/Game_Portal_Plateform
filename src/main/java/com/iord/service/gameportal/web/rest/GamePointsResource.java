package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GamePoints;
import com.iord.service.gameportal.service.GamePointsService;
import com.iord.service.gameportal.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.iord.service.gameportal.domain.GamePoints}.
 */
@RestController
@RequestMapping("/api")
public class GamePointsResource {

    private final Logger log = LoggerFactory.getLogger(GamePointsResource.class);

    private static final String ENTITY_NAME = "gamePoints";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamePointsService gamePointsService;

    public GamePointsResource(GamePointsService gamePointsService) {
        this.gamePointsService = gamePointsService;
    }

    /**
     * {@code POST  /game-points} : Create a new gamePoints.
     *
     * @param gamePoints the gamePoints to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gamePoints, or with status {@code 400 (Bad Request)} if the gamePoints has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-points")
    public ResponseEntity<GamePoints> createGamePoints(@RequestBody GamePoints gamePoints) throws URISyntaxException {
        log.debug("REST request to save GamePoints : {}", gamePoints);
        if (gamePoints.getId() != null) {
            throw new BadRequestAlertException("A new gamePoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePoints result = gamePointsService.save(gamePoints);
        return ResponseEntity.created(new URI("/api/game-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-points} : Updates an existing gamePoints.
     *
     * @param gamePoints the gamePoints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePoints,
     * or with status {@code 400 (Bad Request)} if the gamePoints is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gamePoints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-points")
    public ResponseEntity<GamePoints> updateGamePoints(@RequestBody GamePoints gamePoints) throws URISyntaxException {
        log.debug("REST request to update GamePoints : {}", gamePoints);
        if (gamePoints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GamePoints result = gamePointsService.save(gamePoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePoints.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-points} : get all the gamePoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gamePoints in body.
     */
    @GetMapping("/game-points")
    public ResponseEntity<List<GamePoints>> getAllGamePoints(Pageable pageable) {
        log.debug("REST request to get a page of GamePoints");
        Page<GamePoints> page = gamePointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-points/:id} : get the "id" gamePoints.
     *
     * @param id the id of the gamePoints to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gamePoints, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-points/{id}")
    public ResponseEntity<GamePoints> getGamePoints(@PathVariable Long id) {
        log.debug("REST request to get GamePoints : {}", id);
        Optional<GamePoints> gamePoints = gamePointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gamePoints);
    }

    /**
     * {@code DELETE  /game-points/:id} : delete the "id" gamePoints.
     *
     * @param id the id of the gamePoints to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-points/{id}")
    public ResponseEntity<Void> deleteGamePoints(@PathVariable Long id) {
        log.debug("REST request to delete GamePoints : {}", id);
        gamePointsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
