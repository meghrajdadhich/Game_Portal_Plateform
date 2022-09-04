package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GamePoints;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GamePoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePointsRepository extends JpaRepository<GamePoints, Long> {
}
