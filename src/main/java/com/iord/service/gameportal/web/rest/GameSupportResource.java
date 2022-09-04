package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameSupport;
import com.iord.service.gameportal.service.GameSupportService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameSupport}.
 */
@RestController
@RequestMapping("/api")
public class GameSupportResource {

    private final Logger log = LoggerFactory.getLogger(GameSupportResource.class);

    private static final String ENTITY_NAME = "gameSupport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameSupportService gameSupportService;

    public GameSupportResource(GameSupportService gameSupportService) {
        this.gameSupportService = gameSupportService;
    }

    /**
     * {@code POST  /game-supports} : Create a new gameSupport.
     *
     * @param gameSupport the gameSupport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameSupport, or with status {@code 400 (Bad Request)} if the gameSupport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-supports")
    public ResponseEntity<GameSupport> createGameSupport(@RequestBody GameSupport gameSupport) throws URISyntaxException {
        log.debug("REST request to save GameSupport : {}", gameSupport);
        if (gameSupport.getId() != null) {
            throw new BadRequestAlertException("A new gameSupport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameSupport result = gameSupportService.save(gameSupport);
        return ResponseEntity.created(new URI("/api/game-supports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-supports} : Updates an existing gameSupport.
     *
     * @param gameSupport the gameSupport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameSupport,
     * or with status {@code 400 (Bad Request)} if the gameSupport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameSupport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-supports")
    public ResponseEntity<GameSupport> updateGameSupport(@RequestBody GameSupport gameSupport) throws URISyntaxException {
        log.debug("REST request to update GameSupport : {}", gameSupport);
        if (gameSupport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameSupport result = gameSupportService.save(gameSupport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameSupport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-supports} : get all the gameSupports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameSupports in body.
     */
    @GetMapping("/game-supports")
    public ResponseEntity<List<GameSupport>> getAllGameSupports(Pageable pageable) {
        log.debug("REST request to get a page of GameSupports");
        Page<GameSupport> page = gameSupportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-supports/:id} : get the "id" gameSupport.
     *
     * @param id the id of the gameSupport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameSupport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-supports/{id}")
    public ResponseEntity<GameSupport> getGameSupport(@PathVariable Long id) {
        log.debug("REST request to get GameSupport : {}", id);
        Optional<GameSupport> gameSupport = gameSupportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameSupport);
    }

    /**
     * {@code DELETE  /game-supports/:id} : delete the "id" gameSupport.
     *
     * @param id the id of the gameSupport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-supports/{id}")
    public ResponseEntity<Void> deleteGameSupport(@PathVariable Long id) {
        log.debug("REST request to delete GameSupport : {}", id);
        gameSupportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
