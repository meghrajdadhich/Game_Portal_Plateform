package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GamePlatformService;
import com.iord.service.gameportal.domain.GamePlatform;
import com.iord.service.gameportal.repository.GamePlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GamePlatform}.
 */
@Service
@Transactional
public class GamePlatformServiceImpl implements GamePlatformService {

    private final Logger log = LoggerFactory.getLogger(GamePlatformServiceImpl.class);

    private final GamePlatformRepository gamePlatformRepository;

    public GamePlatformServiceImpl(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }

    @Override
    public GamePlatform save(GamePlatform gamePlatform) {
        log.debug("Request to save GamePlatform : {}", gamePlatform);
        return gamePlatformRepository.save(gamePlatform);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GamePlatform> findAll(Pageable pageable) {
        log.debug("Request to get all GamePlatforms");
        return gamePlatformRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GamePlatform> findOne(Long id) {
        log.debug("Request to get GamePlatform : {}", id);
        return gamePlatformRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GamePlatform : {}", id);
        gamePlatformRepository.deleteById(id);
    }
}
