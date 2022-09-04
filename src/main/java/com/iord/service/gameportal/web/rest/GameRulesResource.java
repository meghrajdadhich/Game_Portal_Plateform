package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameRules;
import com.iord.service.gameportal.service.GameRulesService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameRules}.
 */
@RestController
@RequestMapping("/api")
public class GameRulesResource {

    private final Logger log = LoggerFactory.getLogger(GameRulesResource.class);

    private static final String ENTITY_NAME = "gameRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameRulesService gameRulesService;

    public GameRulesResource(GameRulesService gameRulesService) {
        this.gameRulesService = gameRulesService;
    }

    /**
     * {@code POST  /game-rules} : Create a new gameRules.
     *
     * @param gameRules the gameRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameRules, or with status {@code 400 (Bad Request)} if the gameRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-rules")
    public ResponseEntity<GameRules> createGameRules(@RequestBody GameRules gameRules) throws URISyntaxException {
        log.debug("REST request to save GameRules : {}", gameRules);
        if (gameRules.getId() != null) {
            throw new BadRequestAlertException("A new gameRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameRules result = gameRulesService.save(gameRules);
        return ResponseEntity.created(new URI("/api/game-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-rules} : Updates an existing gameRules.
     *
     * @param gameRules the gameRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameRules,
     * or with status {@code 400 (Bad Request)} if the gameRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-rules")
    public ResponseEntity<GameRules> updateGameRules(@RequestBody GameRules gameRules) throws URISyntaxException {
        log.debug("REST request to update GameRules : {}", gameRules);
        if (gameRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameRules result = gameRulesService.save(gameRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-rules} : get all the gameRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameRules in body.
     */
    @GetMapping("/game-rules")
    public ResponseEntity<List<GameRules>> getAllGameRules(Pageable pageable) {
        log.debug("REST request to get a page of GameRules");
        Page<GameRules> page = gameRulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-rules/:id} : get the "id" gameRules.
     *
     * @param id the id of the gameRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-rules/{id}")
    public ResponseEntity<GameRules> getGameRules(@PathVariable Long id) {
        log.debug("REST request to get GameRules : {}", id);
        Optional<GameRules> gameRules = gameRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameRules);
    }

    /**
     * {@code DELETE  /game-rules/:id} : delete the "id" gameRules.
     *
     * @param id the id of the gameRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-rules/{id}")
    public ResponseEntity<Void> deleteGameRules(@PathVariable Long id) {
        log.debug("REST request to delete GameRules : {}", id);
        gameRulesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
