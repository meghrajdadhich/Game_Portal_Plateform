package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameRating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameRating}.
 */
public interface GameRatingService {

    /**
     * Save a gameRating.
     *
     * @param gameRating the entity to save.
     * @return the persisted entity.
     */
    GameRating save(GameRating gameRating);

    /**
     * Get all the gameRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameRating> findAll(Pageable pageable);


    /**
     * Get the "id" gameRating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameRating> findOne(Long id);

    /**
     * Delete the "id" gameRating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
