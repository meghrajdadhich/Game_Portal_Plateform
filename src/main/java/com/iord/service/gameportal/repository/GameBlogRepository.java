package com.iord.service.gameportal.repository;

import com.iord.service.gameportal.domain.GameBlog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameBlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameBlogRepository extends JpaRepository<GameBlog, Long> {
}
