package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GamePlatform;
import com.iord.service.gameportal.service.GamePlatformService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GamePlatform}.
 */
@RestController
@RequestMapping("/api")
public class GamePlatformResource {

    private final Logger log = LoggerFactory.getLogger(GamePlatformResource.class);

    private static final String ENTITY_NAME = "gamePlatform";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamePlatformService gamePlatformService;

    public GamePlatformResource(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    /**
     * {@code POST  /game-platforms} : Create a new gamePlatform.
     *
     * @param gamePlatform the gamePlatform to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gamePlatform, or with status {@code 400 (Bad Request)} if the gamePlatform has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-platforms")
    public ResponseEntity<GamePlatform> createGamePlatform(@RequestBody GamePlatform gamePlatform) throws URISyntaxException {
        log.debug("REST request to save GamePlatform : {}", gamePlatform);
        if (gamePlatform.getId() != null) {
            throw new BadRequestAlertException("A new gamePlatform cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePlatform result = gamePlatformService.save(gamePlatform);
        return ResponseEntity.created(new URI("/api/game-platforms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-platforms} : Updates an existing gamePlatform.
     *
     * @param gamePlatform the gamePlatform to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePlatform,
     * or with status {@code 400 (Bad Request)} if the gamePlatform is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gamePlatform couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-platforms")
    public ResponseEntity<GamePlatform> updateGamePlatform(@RequestBody GamePlatform gamePlatform) throws URISyntaxException {
        log.debug("REST request to update GamePlatform : {}", gamePlatform);
        if (gamePlatform.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GamePlatform result = gamePlatformService.save(gamePlatform);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePlatform.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-platforms} : get all the gamePlatforms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gamePlatforms in body.
     */
    @GetMapping("/game-platforms")
    public ResponseEntity<List<GamePlatform>> getAllGamePlatforms(Pageable pageable) {
        log.debug("REST request to get a page of GamePlatforms");
        Page<GamePlatform> page = gamePlatformService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-platforms/:id} : get the "id" gamePlatform.
     *
     * @param id the id of the gamePlatform to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gamePlatform, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-platforms/{id}")
    public ResponseEntity<GamePlatform> getGamePlatform(@PathVariable Long id) {
        log.debug("REST request to get GamePlatform : {}", id);
        Optional<GamePlatform> gamePlatform = gamePlatformService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gamePlatform);
    }

    /**
     * {@code DELETE  /game-platforms/:id} : delete the "id" gamePlatform.
     *
     * @param id the id of the gamePlatform to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-platforms/{id}")
    public ResponseEntity<Void> deleteGamePlatform(@PathVariable Long id) {
        log.debug("REST request to delete GamePlatform : {}", id);
        gamePlatformService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
