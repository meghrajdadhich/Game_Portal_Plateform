package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameSupport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameSupport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameSupportRepository extends JpaRepository<GameSupport, Long> {
}
