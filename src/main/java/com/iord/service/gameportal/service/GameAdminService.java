package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameAdmin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameAdmin}.
 */
public interface GameAdminService {

    /**
     * Save a gameAdmin.
     *
     * @param gameAdmin the entity to save.
     * @return the persisted entity.
     */
    GameAdmin save(GameAdmin gameAdmin);

    /**
     * Get all the gameAdmins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameAdmin> findAll(Pageable pageable);


    /**
     * Get the "id" gameAdmin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameAdmin> findOne(Long id);

    /**
     * Delete the "id" gameAdmin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
