package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.Comments;
import com.iord.service.gameportal.service.CommentsService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.Comments}.
 */
@RestController
@RequestMapping("/api")
public class CommentsResource {

    private final Logger log = LoggerFactory.getLogger(CommentsResource.class);

    private static final String ENTITY_NAME = "comments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsService commentsService;

    public CommentsResource(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * {@code POST  /comments} : Create a new comments.
     *
     * @param comments the comments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comments, or with status {@code 400 (Bad Request)} if the comments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<Comments> createComments(@RequestBody Comments comments) throws URISyntaxException {
        log.debug("REST request to save Comments : {}", comments);
        if (comments.getId() != null) {
            throw new BadRequestAlertException("A new comments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comments result = commentsService.save(comments);
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments} : Updates an existing comments.
     *
     * @param comments the comments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comments,
     * or with status {@code 400 (Bad Request)} if the comments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments")
    public ResponseEntity<Comments> updateComments(@RequestBody Comments comments) throws URISyntaxException {
        log.debug("REST request to update Comments : {}", comments);
        if (comments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comments result = commentsService.save(comments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comments.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> getAllComments(Pageable pageable) {
        log.debug("REST request to get a page of Comments");
        Page<Comments> page = commentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comments.
     *
     * @param id the id of the comments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<Comments> getComments(@PathVariable Long id) {
        log.debug("REST request to get Comments : {}", id);
        Optional<Comments> comments = commentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comments);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comments.
     *
     * @param id the id of the comments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long id) {
        log.debug("REST request to delete Comments : {}", id);
        commentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
