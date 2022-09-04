package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameManagerService;
import com.iord.service.gameportal.domain.GameManager;
import com.iord.service.gameportal.repository.GameManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameManager}.
 */
@Service
@Transactional
public class GameManagerServiceImpl implements GameManagerService {

    private final Logger log = LoggerFactory.getLogger(GameManagerServiceImpl.class);

    private final GameManagerRepository gameManagerRepository;

    public GameManagerServiceImpl(GameManagerRepository gameManagerRepository) {
        this.gameManagerRepository = gameManagerRepository;
    }

    @Override
    public GameManager save(GameManager gameManager) {
        log.debug("Request to save GameManager : {}", gameManager);
        return gameManagerRepository.save(gameManager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameManager> findAll(Pageable pageable) {
        log.debug("Request to get all GameManagers");
        return gameManagerRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameManager> findOne(Long id) {
        log.debug("Request to get GameManager : {}", id);
        return gameManagerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameManager : {}", id);
        gameManagerRepository.deleteById(id);
    }
}
