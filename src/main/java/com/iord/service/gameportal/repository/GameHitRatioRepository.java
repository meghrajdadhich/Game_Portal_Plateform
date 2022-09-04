package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameHitRatio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameHitRatio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameHitRatioRepository extends JpaRepository<GameHitRatio, Long> {
}
