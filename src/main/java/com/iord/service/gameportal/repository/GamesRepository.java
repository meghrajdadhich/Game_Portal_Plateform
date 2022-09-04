package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.Games;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Games entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
}
