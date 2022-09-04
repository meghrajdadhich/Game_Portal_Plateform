package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GamePlatform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GamePlatform}.
 */
public interface GamePlatformService {

    /**
     * Save a gamePlatform.
     *
     * @param gamePlatform the entity to save.
     * @return the persisted entity.
     */
    GamePlatform save(GamePlatform gamePlatform);

    /**
     * Get all the gamePlatforms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GamePlatform> findAll(Pageable pageable);


    /**
     * Get the "id" gamePlatform.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GamePlatform> findOne(Long id);

    /**
     * Delete the "id" gamePlatform.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
