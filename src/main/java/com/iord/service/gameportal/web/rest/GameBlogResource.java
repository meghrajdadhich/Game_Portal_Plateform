package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.GameBlog;
import com.iord.service.gameportal.service.GameBlogService;
import com.iord.service.gameportal.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.iord.service.gameportal.domain.GameBlog}.
 */
@RestController
@RequestMapping("/api")
public class GameBlogResource {

    private final Logger log = LoggerFactory.getLogger(GameBlogResource.class);

    private static final String ENTITY_NAME = "gameBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameBlogService gameBlogService;

    public GameBlogResource(GameBlogService gameBlogService) {
        this.gameBlogService = gameBlogService;
    }

    /**
     * {@code POST  /game-blogs} : Create a new gameBlog.
     *
     * @param gameBlog the gameBlog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameBlog, or with status {@code 400 (Bad Request)} if the gameBlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-blogs")
    public ResponseEntity<GameBlog> createGameBlog(@RequestBody GameBlog gameBlog) throws URISyntaxException {
        log.debug("REST request to save GameBlog : {}", gameBlog);
        if (gameBlog.getId() != null) {
            throw new BadRequestAlertException("A new gameBlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameBlog result = gameBlogService.save(gameBlog);
        return ResponseEntity.created(new URI("/api/game-blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-blogs} : Updates an existing gameBlog.
     *
     * @param gameBlog the gameBlog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameBlog,
     * or with status {@code 400 (Bad Request)} if the gameBlog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameBlog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-blogs")
    public ResponseEntity<GameBlog> updateGameBlog(@RequestBody GameBlog gameBlog) throws URISyntaxException {
        log.debug("REST request to update GameBlog : {}", gameBlog);
        if (gameBlog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameBlog result = gameBlogService.save(gameBlog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameBlog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-blogs} : get all the gameBlogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameBlogs in body.
     */
    @GetMapping("/game-blogs")
    public ResponseEntity<List<GameBlog>> getAllGameBlogs(Pageable pageable) {
        log.debug("REST request to get a page of GameBlogs");
        Page<GameBlog> page = gameBlogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-blogs/:id} : get the "id" gameBlog.
     *
     * @param id the id of the gameBlog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-blogs/{id}")
    public ResponseEntity<GameBlog> getGameBlog(@PathVariable Long id) {
        log.debug("REST request to get GameBlog : {}", id);
        Optional<GameBlog> gameBlog = gameBlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameBlog);
    }

    /**
     * {@code DELETE  /game-blogs/:id} : delete the "id" gameBlog.
     *
     * @param id the id of the gameBlog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-blogs/{id}")
    public ResponseEntity<Void> deleteGameBlog(@PathVariable Long id) {
        log.debug("REST request to delete GameBlog : {}", id);
        gameBlogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
