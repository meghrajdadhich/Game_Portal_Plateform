package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameHitRatio;
import com.iord.service.gameportal.service.GameHitRatioService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameHitRatio}.
 */
@RestController
@RequestMapping("/api")
public class GameHitRatioResource {

    private final Logger log = LoggerFactory.getLogger(GameHitRatioResource.class);

    private static final String ENTITY_NAME = "gameHitRatio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameHitRatioService gameHitRatioService;

    public GameHitRatioResource(GameHitRatioService gameHitRatioService) {
        this.gameHitRatioService = gameHitRatioService;
    }

    /**
     * {@code POST  /game-hit-ratios} : Create a new gameHitRatio.
     *
     * @param gameHitRatio the gameHitRatio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameHitRatio, or with status {@code 400 (Bad Request)} if the gameHitRatio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-hit-ratios")
    public ResponseEntity<GameHitRatio> createGameHitRatio(@RequestBody GameHitRatio gameHitRatio) throws URISyntaxException {
        log.debug("REST request to save GameHitRatio : {}", gameHitRatio);
        if (gameHitRatio.getId() != null) {
            throw new BadRequestAlertException("A new gameHitRatio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameHitRatio result = gameHitRatioService.save(gameHitRatio);
        return ResponseEntity.created(new URI("/api/game-hit-ratios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-hit-ratios} : Updates an existing gameHitRatio.
     *
     * @param gameHitRatio the gameHitRatio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameHitRatio,
     * or with status {@code 400 (Bad Request)} if the gameHitRatio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameHitRatio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-hit-ratios")
    public ResponseEntity<GameHitRatio> updateGameHitRatio(@RequestBody GameHitRatio gameHitRatio) throws URISyntaxException {
        log.debug("REST request to update GameHitRatio : {}", gameHitRatio);
        if (gameHitRatio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameHitRatio result = gameHitRatioService.save(gameHitRatio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameHitRatio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-hit-ratios} : get all the gameHitRatios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameHitRatios in body.
     */
    @GetMapping("/game-hit-ratios")
    public ResponseEntity<List<GameHitRatio>> getAllGameHitRatios(Pageable pageable) {
        log.debug("REST request to get a page of GameHitRatios");
        Page<GameHitRatio> page = gameHitRatioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-hit-ratios/:id} : get the "id" gameHitRatio.
     *
     * @param id the id of the gameHitRatio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameHitRatio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-hit-ratios/{id}")
    public ResponseEntity<GameHitRatio> getGameHitRatio(@PathVariable Long id) {
        log.debug("REST request to get GameHitRatio : {}", id);
        Optional<GameHitRatio> gameHitRatio = gameHitRatioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameHitRatio);
    }

    /**
     * {@code DELETE  /game-hit-ratios/:id} : delete the "id" gameHitRatio.
     *
     * @param id the id of the gameHitRatio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-hit-ratios/{id}")
    public ResponseEntity<Void> deleteGameHitRatio(@PathVariable Long id) {
        log.debug("REST request to delete GameHitRatio : {}", id);
        gameHitRatioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
