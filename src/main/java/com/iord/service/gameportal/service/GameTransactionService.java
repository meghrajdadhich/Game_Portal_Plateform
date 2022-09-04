package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameTransaction}.
 */
public interface GameTransactionService {

    /**
     * Save a gameTransaction.
     *
     * @param gameTransaction the entity to save.
     * @return the persisted entity.
     */
    GameTransaction save(GameTransaction gameTransaction);

    /**
     * Get all the gameTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameTransaction> findAll(Pageable pageable);


    /**
     * Get the "id" gameTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameTransaction> findOne(Long id);

    /**
     * Delete the "id" gameTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
