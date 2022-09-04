package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.MembershipHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MembershipHistory}.
 */
public interface MembershipHistoryService {

    /**
     * Save a membershipHistory.
     *
     * @param membershipHistory the entity to save.
     * @return the persisted entity.
     */
    MembershipHistory save(MembershipHistory membershipHistory);

    /**
     * Get all the membershipHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipHistory> findAll(Pageable pageable);


    /**
     * Get the "id" membershipHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembershipHistory> findOne(Long id);

    /**
     * Delete the "id" membershipHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
