package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameCompanyService;
import com.iord.service.gameportal.domain.GameCompany;
import com.iord.service.gameportal.repository.GameCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameCompany}.
 */
@Service
@Transactional
public class GameCompanyServiceImpl implements GameCompanyService {

    private final Logger log = LoggerFactory.getLogger(GameCompanyServiceImpl.class);

    private final GameCompanyRepository gameCompanyRepository;

    public GameCompanyServiceImpl(GameCompanyRepository gameCompanyRepository) {
        this.gameCompanyRepository = gameCompanyRepository;
    }

    @Override
    public GameCompany save(GameCompany gameCompany) {
        log.debug("Request to save GameCompany : {}", gameCompany);
        return gameCompanyRepository.save(gameCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameCompany> findAll(Pageable pageable) {
        log.debug("Request to get all GameCompanies");
        return gameCompanyRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameCompany> findOne(Long id) {
        log.debug("Request to get GameCompany : {}", id);
        return gameCompanyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameCompany : {}", id);
        gameCompanyRepository.deleteById(id);
    }
}
