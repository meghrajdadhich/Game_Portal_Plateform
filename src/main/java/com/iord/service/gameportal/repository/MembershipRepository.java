package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.Membership;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Membership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
