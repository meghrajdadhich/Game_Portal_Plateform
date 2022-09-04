package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GamePlatform;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GamePlatform entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePlatformRepository extends JpaRepository<GamePlatform, Long> {
}
