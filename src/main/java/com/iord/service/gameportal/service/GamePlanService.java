package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GamePlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GamePlan}.
 */
public interface GamePlanService {

    /**
     * Save a gamePlan.
     *
     * @param gamePlan the entity to save.
     * @return the persisted entity.
     */
    GamePlan save(GamePlan gamePlan);

    /**
     * Get all the gamePlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GamePlan> findAll(Pageable pageable);


    /**
     * Get the "id" gamePlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GamePlan> findOne(Long id);

    /**
     * Delete the "id" gamePlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
