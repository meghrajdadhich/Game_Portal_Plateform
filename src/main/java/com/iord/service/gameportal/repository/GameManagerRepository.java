package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameManager;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameManagerRepository extends JpaRepository<GameManager, Long> {
}
