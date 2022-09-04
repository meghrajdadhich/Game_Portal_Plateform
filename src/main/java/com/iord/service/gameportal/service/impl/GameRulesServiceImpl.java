package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameRulesService;
import com.iord.service.gameportal.domain.GameRules;
import com.iord.service.gameportal.repository.GameRulesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameRules}.
 */
@Service
@Transactional
public class GameRulesServiceImpl implements GameRulesService {

    private final Logger log = LoggerFactory.getLogger(GameRulesServiceImpl.class);

    private final GameRulesRepository gameRulesRepository;

    public GameRulesServiceImpl(GameRulesRepository gameRulesRepository) {
        this.gameRulesRepository = gameRulesRepository;
    }

    @Override
    public GameRules save(GameRules gameRules) {
        log.debug("Request to save GameRules : {}", gameRules);
        return gameRulesRepository.save(gameRules);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameRules> findAll(Pageable pageable) {
        log.debug("Request to get all GameRules");
        return gameRulesRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameRules> findOne(Long id) {
        log.debug("Request to get GameRules : {}", id);
        return gameRulesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameRules : {}", id);
        gameRulesRepository.deleteById(id);
    }
}
