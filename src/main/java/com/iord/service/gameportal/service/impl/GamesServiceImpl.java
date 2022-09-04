package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GamesService;
import com.iord.service.gameportal.domain.Games;
import com.iord.service.gameportal.repository.GamesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Games}.
 */
@Service
@Transactional
public class GamesServiceImpl implements GamesService {

    private final Logger log = LoggerFactory.getLogger(GamesServiceImpl.class);

    private final GamesRepository gamesRepository;

    public GamesServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    public Games save(Games games) {
        log.debug("Request to save Games : {}", games);
        return gamesRepository.save(games);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Games> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        return gamesRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Games> findOne(Long id) {
        log.debug("Request to get Games : {}", id);
        return gamesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Games : {}", id);
        gamesRepository.deleteById(id);
    }
}
