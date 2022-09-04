package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameCompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameCompany}.
 */
public interface GameCompanyService {

    /**
     * Save a gameCompany.
     *
     * @param gameCompany the entity to save.
     * @return the persisted entity.
     */
    GameCompany save(GameCompany gameCompany);

    /**
     * Get all the gameCompanies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameCompany> findAll(Pageable pageable);


    /**
     * Get the "id" gameCompany.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameCompany> findOne(Long id);

    /**
     * Delete the "id" gameCompany.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
