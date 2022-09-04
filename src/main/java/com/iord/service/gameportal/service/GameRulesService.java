package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameRules;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameRules}.
 */
public interface GameRulesService {

    /**
     * Save a gameRules.
     *
     * @param gameRules the entity to save.
     * @return the persisted entity.
     */
    GameRules save(GameRules gameRules);

    /**
     * Get all the gameRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameRules> findAll(Pageable pageable);


    /**
     * Get the "id" gameRules.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameRules> findOne(Long id);

    /**
     * Delete the "id" gameRules.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
