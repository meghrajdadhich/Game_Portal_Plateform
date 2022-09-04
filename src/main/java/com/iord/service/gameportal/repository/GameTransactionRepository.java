package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameTransaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameTransactionRepository extends JpaRepository<GameTransaction, Long> {
}
