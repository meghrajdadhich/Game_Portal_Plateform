package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameManager;
import com.iord.service.gameportal.service.GameManagerService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameManager}.
 */
@RestController
@RequestMapping("/api")
public class GameManagerResource {

    private final Logger log = LoggerFactory.getLogger(GameManagerResource.class);

    private static final String ENTITY_NAME = "gameManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameManagerService gameManagerService;

    public GameManagerResource(GameManagerService gameManagerService) {
        this.gameManagerService = gameManagerService;
    }

    /**
     * {@code POST  /game-managers} : Create a new gameManager.
     *
     * @param gameManager the gameManager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameManager, or with status {@code 400 (Bad Request)} if the gameManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-managers")
    public ResponseEntity<GameManager> createGameManager(@RequestBody GameManager gameManager) throws URISyntaxException {
        log.debug("REST request to save GameManager : {}", gameManager);
        if (gameManager.getId() != null) {
            throw new BadRequestAlertException("A new gameManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameManager result = gameManagerService.save(gameManager);
        return ResponseEntity.created(new URI("/api/game-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-managers} : Updates an existing gameManager.
     *
     * @param gameManager the gameManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameManager,
     * or with status {@code 400 (Bad Request)} if the gameManager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-managers")
    public ResponseEntity<GameManager> updateGameManager(@RequestBody GameManager gameManager) throws URISyntaxException {
        log.debug("REST request to update GameManager : {}", gameManager);
        if (gameManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameManager result = gameManagerService.save(gameManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameManager.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-managers} : get all the gameManagers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameManagers in body.
     */
    @GetMapping("/game-managers")
    public ResponseEntity<List<GameManager>> getAllGameManagers(Pageable pageable) {
        log.debug("REST request to get a page of GameManagers");
        Page<GameManager> page = gameManagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-managers/:id} : get the "id" gameManager.
     *
     * @param id the id of the gameManager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameManager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-managers/{id}")
    public ResponseEntity<GameManager> getGameManager(@PathVariable Long id) {
        log.debug("REST request to get GameManager : {}", id);
        Optional<GameManager> gameManager = gameManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameManager);
    }

    /**
     * {@code DELETE  /game-managers/:id} : delete the "id" gameManager.
     *
     * @param id the id of the gameManager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-managers/{id}")
    public ResponseEntity<Void> deleteGameManager(@PathVariable Long id) {
        log.debug("REST request to delete GameManager : {}", id);
        gameManagerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
