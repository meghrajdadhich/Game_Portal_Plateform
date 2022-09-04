package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameManager}.
 */
public interface GameManagerService {

    /**
     * Save a gameManager.
     *
     * @param gameManager the entity to save.
     * @return the persisted entity.
     */
    GameManager save(GameManager gameManager);

    /**
     * Get all the gameManagers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameManager> findAll(Pageable pageable);


    /**
     * Get the "id" gameManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameManager> findOne(Long id);

    /**
     * Delete the "id" gameManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
