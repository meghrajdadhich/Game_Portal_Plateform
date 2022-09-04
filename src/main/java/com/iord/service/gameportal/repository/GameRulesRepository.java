package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameRules;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRulesRepository extends JpaRepository<GameRules, Long> {
}
