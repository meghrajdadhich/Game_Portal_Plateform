package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.Comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Comments}.
 */
public interface CommentsService {

    /**
     * Save a comments.
     *
     * @param comments the entity to save.
     * @return the persisted entity.
     */
    Comments save(Comments comments);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Comments> findAll(Pageable pageable);


    /**
     * Get the "id" comments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comments> findOne(Long id);

    /**
     * Delete the "id" comments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
