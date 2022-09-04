package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameCompany;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameCompanyRepository extends JpaRepository<GameCompany, Long> {
}
