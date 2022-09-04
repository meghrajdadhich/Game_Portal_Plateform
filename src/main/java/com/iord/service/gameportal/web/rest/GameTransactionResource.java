package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameTransaction;
import com.iord.service.gameportal.service.GameTransactionService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameTransaction}.
 */
@RestController
@RequestMapping("/api")
public class GameTransactionResource {

    private final Logger log = LoggerFactory.getLogger(GameTransactionResource.class);

    private static final String ENTITY_NAME = "gameTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameTransactionService gameTransactionService;

    public GameTransactionResource(GameTransactionService gameTransactionService) {
        this.gameTransactionService = gameTransactionService;
    }

    /**
     * {@code POST  /game-transactions} : Create a new gameTransaction.
     *
     * @param gameTransaction the gameTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameTransaction, or with status {@code 400 (Bad Request)} if the gameTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-transactions")
    public ResponseEntity<GameTransaction> createGameTransaction(@RequestBody GameTransaction gameTransaction) throws URISyntaxException {
        log.debug("REST request to save GameTransaction : {}", gameTransaction);
        if (gameTransaction.getId() != null) {
            throw new BadRequestAlertException("A new gameTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameTransaction result = gameTransactionService.save(gameTransaction);
        return ResponseEntity.created(new URI("/api/game-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-transactions} : Updates an existing gameTransaction.
     *
     * @param gameTransaction the gameTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameTransaction,
     * or with status {@code 400 (Bad Request)} if the gameTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-transactions")
    public ResponseEntity<GameTransaction> updateGameTransaction(@RequestBody GameTransaction gameTransaction) throws URISyntaxException {
        log.debug("REST request to update GameTransaction : {}", gameTransaction);
        if (gameTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameTransaction result = gameTransactionService.save(gameTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-transactions} : get all the gameTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameTransactions in body.
     */
    @GetMapping("/game-transactions")
    public ResponseEntity<List<GameTransaction>> getAllGameTransactions(Pageable pageable) {
        log.debug("REST request to get a page of GameTransactions");
        Page<GameTransaction> page = gameTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-transactions/:id} : get the "id" gameTransaction.
     *
     * @param id the id of the gameTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-transactions/{id}")
    public ResponseEntity<GameTransaction> getGameTransaction(@PathVariable Long id) {
        log.debug("REST request to get GameTransaction : {}", id);
        Optional<GameTransaction> gameTransaction = gameTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameTransaction);
    }

    /**
     * {@code DELETE  /game-transactions/:id} : delete the "id" gameTransaction.
     *
     * @param id the id of the gameTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-transactions/{id}")
    public ResponseEntity<Void> deleteGameTransaction(@PathVariable Long id) {
        log.debug("REST request to delete GameTransaction : {}", id);
        gameTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
