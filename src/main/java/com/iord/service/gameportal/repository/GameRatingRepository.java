package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameRating;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRatingRepository extends JpaRepository<GameRating, Long> {
}
