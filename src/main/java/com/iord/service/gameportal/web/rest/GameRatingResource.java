package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameRating;
import com.iord.service.gameportal.service.GameRatingService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameRating}.
 */
@RestController
@RequestMapping("/api")
public class GameRatingResource {

    private final Logger log = LoggerFactory.getLogger(GameRatingResource.class);

    private static final String ENTITY_NAME = "gameRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameRatingService gameRatingService;

    public GameRatingResource(GameRatingService gameRatingService) {
        this.gameRatingService = gameRatingService;
    }

    /**
     * {@code POST  /game-ratings} : Create a new gameRating.
     *
     * @param gameRating the gameRating to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameRating, or with status {@code 400 (Bad Request)} if the gameRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-ratings")
    public ResponseEntity<GameRating> createGameRating(@RequestBody GameRating gameRating) throws URISyntaxException {
        log.debug("REST request to save GameRating : {}", gameRating);
        if (gameRating.getId() != null) {
            throw new BadRequestAlertException("A new gameRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameRating result = gameRatingService.save(gameRating);
        return ResponseEntity.created(new URI("/api/game-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-ratings} : Updates an existing gameRating.
     *
     * @param gameRating the gameRating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameRating,
     * or with status {@code 400 (Bad Request)} if the gameRating is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameRating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-ratings")
    public ResponseEntity<GameRating> updateGameRating(@RequestBody GameRating gameRating) throws URISyntaxException {
        log.debug("REST request to update GameRating : {}", gameRating);
        if (gameRating.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameRating result = gameRatingService.save(gameRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameRating.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-ratings} : get all the gameRatings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameRatings in body.
     */
    @GetMapping("/game-ratings")
    public ResponseEntity<List<GameRating>> getAllGameRatings(Pageable pageable) {
        log.debug("REST request to get a page of GameRatings");
        Page<GameRating> page = gameRatingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-ratings/:id} : get the "id" gameRating.
     *
     * @param id the id of the gameRating to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameRating, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-ratings/{id}")
    public ResponseEntity<GameRating> getGameRating(@PathVariable Long id) {
        log.debug("REST request to get GameRating : {}", id);
        Optional<GameRating> gameRating = gameRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameRating);
    }

    /**
     * {@code DELETE  /game-ratings/:id} : delete the "id" gameRating.
     *
     * @param id the id of the gameRating to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-ratings/{id}")
    public ResponseEntity<Void> deleteGameRating(@PathVariable Long id) {
        log.debug("REST request to delete GameRating : {}", id);
        gameRatingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
