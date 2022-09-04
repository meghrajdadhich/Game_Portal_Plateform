package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GamePrivacy;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GamePrivacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePrivacyRepository extends JpaRepository<GamePrivacy, Long> {
}
