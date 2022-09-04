package com.iord.service.gameportal.service;

import com.iord.service.gameportal.domain.GameBlog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GameBlog}.
 */
public interface GameBlogService {

    /**
     * Save a gameBlog.
     *
     * @param gameBlog the entity to save.
     * @return the persisted entity.
     */
    GameBlog save(GameBlog gameBlog);

    /**
     * Get all the gameBlogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameBlog> findAll(Pageable pageable);


    /**
     * Get the "id" gameBlog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameBlog> findOne(Long id);

    /**
     * Delete the "id" gameBlog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
