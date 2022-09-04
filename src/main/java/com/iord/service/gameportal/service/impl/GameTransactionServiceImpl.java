package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GameTransactionService;
import com.iord.service.gameportal.domain.GameTransaction;
import com.iord.service.gameportal.repository.GameTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameTransaction}.
 */
@Service
@Transactional
public class GameTransactionServiceImpl implements GameTransactionService {

    private final Logger log = LoggerFactory.getLogger(GameTransactionServiceImpl.class);

    private final GameTransactionRepository gameTransactionRepository;

    public GameTransactionServiceImpl(GameTransactionRepository gameTransactionRepository) {
        this.gameTransactionRepository = gameTransactionRepository;
    }

    @Override
    public GameTransaction save(GameTransaction gameTransaction) {
        log.debug("Request to save GameTransaction : {}", gameTransaction);
        return gameTransactionRepository.save(gameTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameTransaction> findAll(Pageable pageable) {
        log.debug("Request to get all GameTransactions");
        return gameTransactionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GameTransaction> findOne(Long id) {
        log.debug("Request to get GameTransaction : {}", id);
        return gameTransactionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameTransaction : {}", id);
        gameTransactionRepository.deleteById(id);
    }
}
