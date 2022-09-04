package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameAdminService;
import com.iord.service.gameportal.domain.GameAdmin;
import com.iord.service.gameportal.repository.GameAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameAdmin}.
 */
@Service
@Transactional
public class GameAdminServiceImpl implements GameAdminService {

    private final Logger log = LoggerFactory.getLogger(GameAdminServiceImpl.class);

    private final GameAdminRepository gameAdminRepository;

    public GameAdminServiceImpl(GameAdminRepository gameAdminRepository) {
        this.gameAdminRepository = gameAdminRepository;
    }

    @Override
    public GameAdmin save(GameAdmin gameAdmin) {
        log.debug("Request to save GameAdmin : {}", gameAdmin);
        return gameAdminRepository.save(gameAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameAdmin> findAll(Pageable pageable) {
        log.debug("Request to get all GameAdmins");
        return gameAdminRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameAdmin> findOne(Long id) {
        log.debug("Request to get GameAdmin : {}", id);
        return gameAdminRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameAdmin : {}", id);
        gameAdminRepository.deleteById(id);
    }
}
