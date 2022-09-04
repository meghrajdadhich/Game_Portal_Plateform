package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameSupport}.
 */
public interface GameSupportService {

    /**
     * Save a gameSupport.
     *
     * @param gameSupport the entity to save.
     * @return the persisted entity.
     */
    GameSupport save(GameSupport gameSupport);

    /**
     * Get all the gameSupports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameSupport> findAll(Pageable pageable);


    /**
     * Get the "id" gameSupport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameSupport> findOne(Long id);

    /**
     * Delete the "id" gameSupport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
