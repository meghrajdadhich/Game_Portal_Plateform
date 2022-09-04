package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameCompany;
import com.iord.service.gameportal.service.GameCompanyService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameCompany}.
 */
@RestController
@RequestMapping("/api")
public class GameCompanyResource {

    private final Logger log = LoggerFactory.getLogger(GameCompanyResource.class);

    private static final String ENTITY_NAME = "gameCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameCompanyService gameCompanyService;

    public GameCompanyResource(GameCompanyService gameCompanyService) {
        this.gameCompanyService = gameCompanyService;
    }

    /**
     * {@code POST  /game-companies} : Create a new gameCompany.
     *
     * @param gameCompany the gameCompany to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameCompany, or with status {@code 400 (Bad Request)} if the gameCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-companies")
    public ResponseEntity<GameCompany> createGameCompany(@RequestBody GameCompany gameCompany) throws URISyntaxException {
        log.debug("REST request to save GameCompany : {}", gameCompany);
        if (gameCompany.getId() != null) {
            throw new BadRequestAlertException("A new gameCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameCompany result = gameCompanyService.save(gameCompany);
        return ResponseEntity.created(new URI("/api/game-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-companies} : Updates an existing gameCompany.
     *
     * @param gameCompany the gameCompany to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameCompany,
     * or with status {@code 400 (Bad Request)} if the gameCompany is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameCompany couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-companies")
    public ResponseEntity<GameCompany> updateGameCompany(@RequestBody GameCompany gameCompany) throws URISyntaxException {
        log.debug("REST request to update GameCompany : {}", gameCompany);
        if (gameCompany.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameCompany result = gameCompanyService.save(gameCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameCompany.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-companies} : get all the gameCompanies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameCompanies in body.
     */
    @GetMapping("/game-companies")
    public ResponseEntity<List<GameCompany>> getAllGameCompanies(Pageable pageable) {
        log.debug("REST request to get a page of GameCompanies");
        Page<GameCompany> page = gameCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-companies/:id} : get the "id" gameCompany.
     *
     * @param id the id of the gameCompany to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameCompany, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-companies/{id}")
    public ResponseEntity<GameCompany> getGameCompany(@PathVariable Long id) {
        log.debug("REST request to get GameCompany : {}", id);
        Optional<GameCompany> gameCompany = gameCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameCompany);
    }

    /**
     * {@code DELETE  /game-companies/:id} : delete the "id" gameCompany.
     *
     * @param id the id of the gameCompany to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-companies/{id}")
    public ResponseEntity<Void> deleteGameCompany(@PathVariable Long id) {
        log.debug("REST request to delete GameCompany : {}", id);
        gameCompanyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
