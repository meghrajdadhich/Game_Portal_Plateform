package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.Comments;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Comments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
