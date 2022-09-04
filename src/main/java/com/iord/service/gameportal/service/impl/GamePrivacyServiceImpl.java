package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GamePrivacyService;
import com.iord.service.gameportal.domain.GamePrivacy;
import com.iord.service.gameportal.repository.GamePrivacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GamePrivacy}.
 */
@Service
@Transactional
public class GamePrivacyServiceImpl implements GamePrivacyService {

    private final Logger log = LoggerFactory.getLogger(GamePrivacyServiceImpl.class);

    private final GamePrivacyRepository gamePrivacyRepository;

    public GamePrivacyServiceImpl(GamePrivacyRepository gamePrivacyRepository) {
        this.gamePrivacyRepository = gamePrivacyRepository;
    }

    @Override
    public GamePrivacy save(GamePrivacy gamePrivacy) {
        log.debug("Request to save GamePrivacy : {}", gamePrivacy);
        return gamePrivacyRepository.save(gamePrivacy);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GamePrivacy> findAll(Pageable pageable) {
        log.debug("Request to get all GamePrivacies");
        return gamePrivacyRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GamePrivacy> findOne(Long id) {
        log.debug("Request to get GamePrivacy : {}", id);
        return gamePrivacyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GamePrivacy : {}", id);
        gamePrivacyRepository.deleteById(id);
    }
}
