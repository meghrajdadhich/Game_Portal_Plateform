package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.CommentsService;
import com.iord.service.gameportal.domain.Comments;
import com.iord.service.gameportal.repository.CommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Comments}.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Comments save(Comments comments) {
        log.debug("Request to save Comments : {}", comments);
        return commentsRepository.save(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comments> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Comments> findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        return commentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.deleteById(id);
    }
}
