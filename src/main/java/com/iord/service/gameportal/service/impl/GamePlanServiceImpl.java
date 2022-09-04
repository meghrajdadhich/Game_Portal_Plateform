package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.GamePlanService;
import com.iord.service.gameportal.domain.GamePlan;
import com.iord.service.gameportal.repository.GamePlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GamePlan}.
 */
@Service
@Transactional
public class GamePlanServiceImpl implements GamePlanService {

    private final Logger log = LoggerFactory.getLogger(GamePlanServiceImpl.class);

    private final GamePlanRepository gamePlanRepository;

    public GamePlanServiceImpl(GamePlanRepository gamePlanRepository) {
        this.gamePlanRepository = gamePlanRepository;
    }

    @Override
    public GamePlan save(GamePlan gamePlan) {
        log.debug("Request to save GamePlan : {}", gamePlan);
        return gamePlanRepository.save(gamePlan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GamePlan> findAll(Pageable pageable) {
        log.debug("Request to get all GamePlans");
        return gamePlanRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GamePlan> findOne(Long id) {
        log.debug("Request to get GamePlan : {}", id);
        return gamePlanRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GamePlan : {}", id);
        gamePlanRepository.deleteById(id);
    }
}
