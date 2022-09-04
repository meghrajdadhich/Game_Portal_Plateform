package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GamePlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GamePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePlanRepository extends JpaRepository<GamePlan, Long> {
}
