package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameSupportService;
import com.iord.service.gameportal.domain.GameSupport;
import com.iord.service.gameportal.repository.GameSupportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameSupport}.
 */
@Service
@Transactional
public class GameSupportServiceImpl implements GameSupportService {

    private final Logger log = LoggerFactory.getLogger(GameSupportServiceImpl.class);

    private final GameSupportRepository gameSupportRepository;

    public GameSupportServiceImpl(GameSupportRepository gameSupportRepository) {
        this.gameSupportRepository = gameSupportRepository;
    }

    @Override
    public GameSupport save(GameSupport gameSupport) {
        log.debug("Request to save GameSupport : {}", gameSupport);
        return gameSupportRepository.save(gameSupport);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameSupport> findAll(Pageable pageable) {
        log.debug("Request to get all GameSupports");
        return gameSupportRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameSupport> findOne(Long id) {
        log.debug("Request to get GameSupport : {}", id);
        return gameSupportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameSupport : {}", id);
        gameSupportRepository.deleteById(id);
    }
}
