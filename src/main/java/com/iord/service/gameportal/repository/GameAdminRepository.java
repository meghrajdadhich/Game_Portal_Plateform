package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameAdmin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameAdminRepository extends JpaRepository<GameAdmin, Long> {
}
