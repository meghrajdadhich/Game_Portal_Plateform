package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.Games;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Games}.
 */
public interface GamesService {

    /**
     * Save a games.
     *
     * @param games the entity to save.
     * @return the persisted entity.
     */
    Games save(Games games);

    /**
     * Get all the games.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Games> findAll(Pageable pageable);


    /**
     * Get the "id" games.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Games> findOne(Long id);

    /**
     * Delete the "id" games.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
