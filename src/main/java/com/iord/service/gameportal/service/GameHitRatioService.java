package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameHitRatio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameHitRatio}.
 */
public interface GameHitRatioService {

    /**
     * Save a gameHitRatio.
     *
     * @param gameHitRatio the entity to save.
     * @return the persisted entity.
     */
    GameHitRatio save(GameHitRatio gameHitRatio);

    /**
     * Get all the gameHitRatios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameHitRatio> findAll(Pageable pageable);


    /**
     * Get the "id" gameHitRatio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameHitRatio> findOne(Long id);

    /**
     * Delete the "id" gameHitRatio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
