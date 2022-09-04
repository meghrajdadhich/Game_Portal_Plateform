package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameHitRatioService;
import com.iord.service.gameportal.domain.GameHitRatio;
import com.iord.service.gameportal.repository.GameHitRatioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameHitRatio}.
 */
@Service
@Transactional
public class GameHitRatioServiceImpl implements GameHitRatioService {

    private final Logger log = LoggerFactory.getLogger(GameHitRatioServiceImpl.class);

    private final GameHitRatioRepository gameHitRatioRepository;

    public GameHitRatioServiceImpl(GameHitRatioRepository gameHitRatioRepository) {
        this.gameHitRatioRepository = gameHitRatioRepository;
    }

    @Override
    public GameHitRatio save(GameHitRatio gameHitRatio) {
        log.debug("Request to save GameHitRatio : {}", gameHitRatio);
        return gameHitRatioRepository.save(gameHitRatio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameHitRatio> findAll(Pageable pageable) {
        log.debug("Request to get all GameHitRatios");
        return gameHitRatioRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameHitRatio> findOne(Long id) {
        log.debug("Request to get GameHitRatio : {}", id);
        return gameHitRatioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameHitRatio : {}", id);
        gameHitRatioRepository.deleteById(id);
    }
}
