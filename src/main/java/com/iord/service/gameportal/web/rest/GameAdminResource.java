package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameAdmin;
import com.iord.service.gameportal.service.GameAdminService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameAdmin}.
 */
@RestController
@RequestMapping("/api")
public class GameAdminResource {

    private final Logger log = LoggerFactory.getLogger(GameAdminResource.class);

    private static final String ENTITY_NAME = "gameAdmin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameAdminService gameAdminService;

    public GameAdminResource(GameAdminService gameAdminService) {
        this.gameAdminService = gameAdminService;
    }

    /**
     * {@code POST  /game-admins} : Create a new gameAdmin.
     *
     * @param gameAdmin the gameAdmin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameAdmin, or with status {@code 400 (Bad Request)} if the gameAdmin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-admins")
    public ResponseEntity<GameAdmin> createGameAdmin(@RequestBody GameAdmin gameAdmin) throws URISyntaxException {
        log.debug("REST request to save GameAdmin : {}", gameAdmin);
        if (gameAdmin.getId() != null) {
            throw new BadRequestAlertException("A new gameAdmin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameAdmin result = gameAdminService.save(gameAdmin);
        return ResponseEntity.created(new URI("/api/game-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-admins} : Updates an existing gameAdmin.
     *
     * @param gameAdmin the gameAdmin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameAdmin,
     * or with status {@code 400 (Bad Request)} if the gameAdmin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameAdmin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-admins")
    public ResponseEntity<GameAdmin> updateGameAdmin(@RequestBody GameAdmin gameAdmin) throws URISyntaxException {
        log.debug("REST request to update GameAdmin : {}", gameAdmin);
        if (gameAdmin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameAdmin result = gameAdminService.save(gameAdmin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameAdmin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-admins} : get all the gameAdmins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameAdmins in body.
     */
    @GetMapping("/game-admins")
    public ResponseEntity<List<GameAdmin>> getAllGameAdmins(Pageable pageable) {
        log.debug("REST request to get a page of GameAdmins");
        Page<GameAdmin> page = gameAdminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-admins/:id} : get the "id" gameAdmin.
     *
     * @param id the id of the gameAdmin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameAdmin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-admins/{id}")
    public ResponseEntity<GameAdmin> getGameAdmin(@PathVariable Long id) {
        log.debug("REST request to get GameAdmin : {}", id);
        Optional<GameAdmin> gameAdmin = gameAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameAdmin);
    }

    /**
     * {@code DELETE  /game-admins/:id} : delete the "id" gameAdmin.
     *
     * @param id the id of the gameAdmin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-admins/{id}")
    public ResponseEntity<Void> deleteGameAdmin(@PathVariable Long id) {
        log.debug("REST request to delete GameAdmin : {}", id);
        gameAdminService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
