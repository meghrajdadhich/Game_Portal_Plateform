package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameRatingService;
import com.iord.service.gameportal.domain.GameRating;
import com.iord.service.gameportal.repository.GameRatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameRating}.
 */
@Service
@Transactional
public class GameRatingServiceImpl implements GameRatingService {

    private final Logger log = LoggerFactory.getLogger(GameRatingServiceImpl.class);

    private final GameRatingRepository gameRatingRepository;

    public GameRatingServiceImpl(GameRatingRepository gameRatingRepository) {
        this.gameRatingRepository = gameRatingRepository;
    }

    @Override
    public GameRating save(GameRating gameRating) {
        log.debug("Request to save GameRating : {}", gameRating);
        return gameRatingRepository.save(gameRating);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameRating> findAll(Pageable pageable) {
        log.debug("Request to get all GameRatings");
        return gameRatingRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameRating> findOne(Long id) {
        log.debug("Request to get GameRating : {}", id);
        return gameRatingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameRating : {}", id);
        gameRatingRepository.deleteById(id);
    }
}
