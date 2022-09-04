package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.MembershipHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MembershipHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipHistoryRepository extends JpaRepository<MembershipHistory, Long> {
}
