package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GamePoints;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GamePoints}.
 */
public interface GamePointsService {

    /**
     * Save a gamePoints.
     *
     * @param gamePoints the entity to save.
     * @return the persisted entity.
     */
    GamePoints save(GamePoints gamePoints);

    /**
     * Get all the gamePoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GamePoints> findAll(Pageable pageable);


    /**
     * Get the "id" gamePoints.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GamePoints> findOne(Long id);

    /**
     * Delete the "id" gamePoints.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
