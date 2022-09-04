package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameBlogService;
import com.iord.service.gameportal.domain.GameBlog;
import com.iord.service.gameportal.repository.GameBlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameBlog}.
 */
@Service
@Transactional
public class GameBlogServiceImpl implements GameBlogService {

    private final Logger log = LoggerFactory.getLogger(GameBlogServiceImpl.class);

    private final GameBlogRepository gameBlogRepository;

    public GameBlogServiceImpl(GameBlogRepository gameBlogRepository) {
        this.gameBlogRepository = gameBlogRepository;
    }

    @Override
    public GameBlog save(GameBlog gameBlog) {
        log.debug("Request to save GameBlog : {}", gameBlog);
        return gameBlogRepository.save(gameBlog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameBlog> findAll(Pageable pageable) {
        log.debug("Request to get all GameBlogs");
        return gameBlogRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameBlog> findOne(Long id) {
        log.debug("Request to get GameBlog : {}", id);
        return gameBlogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameBlog : {}", id);
        gameBlogRepository.deleteById(id);
    }
}
