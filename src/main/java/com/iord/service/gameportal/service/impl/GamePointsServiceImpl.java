package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GamePointsService;
import com.iord.service.gameportal.domain.GamePoints;
import com.iord.service.gameportal.repository.GamePointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GamePoints}.
 */
@Service
@Transactional
public class GamePointsServiceImpl implements GamePointsService {

    private final Logger log = LoggerFactory.getLogger(GamePointsServiceImpl.class);

    private final GamePointsRepository gamePointsRepository;

    public GamePointsServiceImpl(GamePointsRepository gamePointsRepository) {
        this.gamePointsRepository = gamePointsRepository;
    }

    @Override
    public GamePoints save(GamePoints gamePoints) {
        log.debug("Request to save GamePoints : {}", gamePoints);
        return gamePointsRepository.save(gamePoints);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GamePoints> findAll(Pageable pageable) {
        log.debug("Request to get all GamePoints");
        return gamePointsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GamePoints> findOne(Long id) {
        log.debug("Request to get GamePoints : {}", id);
        return gamePointsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GamePoints : {}", id);
        gamePointsRepository.deleteById(id);
    }
}
