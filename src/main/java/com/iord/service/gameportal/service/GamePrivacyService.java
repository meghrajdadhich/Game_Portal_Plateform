package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GamePrivacy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GamePrivacy}.
 */
public interface GamePrivacyService {

    /**
     * Save a gamePrivacy.
     *
     * @param gamePrivacy the entity to save.
     * @return the persisted entity.
     */
    GamePrivacy save(GamePrivacy gamePrivacy);

    /**
     * Get all the gamePrivacies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GamePrivacy> findAll(Pageable pageable);


    /**
     * Get the "id" gamePrivacy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GamePrivacy> findOne(Long id);

    /**
     * Delete the "id" gamePrivacy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
