package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GamePrivacy;
import com.iord.service.gameportal.service.GamePrivacyService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GamePrivacy}.
 */
@RestController
@RequestMapping("/api")
public class GamePrivacyResource {

    private final Logger log = LoggerFactory.getLogger(GamePrivacyResource.class);

    private static final String ENTITY_NAME = "gamePrivacy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamePrivacyService gamePrivacyService;

    public GamePrivacyResource(GamePrivacyService gamePrivacyService) {
        this.gamePrivacyService = gamePrivacyService;
    }

    /**
     * {@code POST  /game-privacies} : Create a new gamePrivacy.
     *
     * @param gamePrivacy the gamePrivacy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gamePrivacy, or with status {@code 400 (Bad Request)} if the gamePrivacy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-privacies")
    public ResponseEntity<GamePrivacy> createGamePrivacy(@RequestBody GamePrivacy gamePrivacy) throws URISyntaxException {
        log.debug("REST request to save GamePrivacy : {}", gamePrivacy);
        if (gamePrivacy.getId() != null) {
            throw new BadRequestAlertException("A new gamePrivacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePrivacy result = gamePrivacyService.save(gamePrivacy);
        return ResponseEntity.created(new URI("/api/game-privacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-privacies} : Updates an existing gamePrivacy.
     *
     * @param gamePrivacy the gamePrivacy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePrivacy,
     * or with status {@code 400 (Bad Request)} if the gamePrivacy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gamePrivacy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-privacies")
    public ResponseEntity<GamePrivacy> updateGamePrivacy(@RequestBody GamePrivacy gamePrivacy) throws URISyntaxException {
        log.debug("REST request to update GamePrivacy : {}", gamePrivacy);
        if (gamePrivacy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GamePrivacy result = gamePrivacyService.save(gamePrivacy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePrivacy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-privacies} : get all the gamePrivacies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gamePrivacies in body.
     */
    @GetMapping("/game-privacies")
    public ResponseEntity<List<GamePrivacy>> getAllGamePrivacies(Pageable pageable) {
        log.debug("REST request to get a page of GamePrivacies");
        Page<GamePrivacy> page = gamePrivacyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-privacies/:id} : get the "id" gamePrivacy.
     *
     * @param id the id of the gamePrivacy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gamePrivacy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-privacies/{id}")
    public ResponseEntity<GamePrivacy> getGamePrivacy(@PathVariable Long id) {
        log.debug("REST request to get GamePrivacy : {}", id);
        Optional<GamePrivacy> gamePrivacy = gamePrivacyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gamePrivacy);
    }

    /**
     * {@code DELETE  /game-privacies/:id} : delete the "id" gamePrivacy.
     *
     * @param id the id of the gamePrivacy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-privacies/{id}")
    public ResponseEntity<Void> deleteGamePrivacy(@PathVariable Long id) {
        log.debug("REST request to delete GamePrivacy : {}", id);
        gamePrivacyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
