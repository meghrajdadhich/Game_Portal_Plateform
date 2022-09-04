package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.domain.MembershipHistory;
import com.iord.service.gameportal.service.MembershipHistoryService;
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
 * REST controller for managing {@link com.iord.service.gameportal.domain.MembershipHistory}.
 */
@RestController
@RequestMapping("/api")
public class MembershipHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MembershipHistoryResource.class);

    private static final String ENTITY_NAME = "membershipHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipHistoryService membershipHistoryService;

    public MembershipHistoryResource(MembershipHistoryService membershipHistoryService) {
        this.membershipHistoryService = membershipHistoryService;
    }

    /**
     * {@code POST  /membership-histories} : Create a new membershipHistory.
     *
     * @param membershipHistory the membershipHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipHistory, or with status {@code 400 (Bad Request)} if the membershipHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membership-histories")
    public ResponseEntity<MembershipHistory> createMembershipHistory(@RequestBody MembershipHistory membershipHistory) throws URISyntaxException {
        log.debug("REST request to save MembershipHistory : {}", membershipHistory);
        if (membershipHistory.getId() != null) {
            throw new BadRequestAlertException("A new membershipHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipHistory result = membershipHistoryService.save(membershipHistory);
        return ResponseEntity.created(new URI("/api/membership-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membership-histories} : Updates an existing membershipHistory.
     *
     * @param membershipHistory the membershipHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipHistory,
     * or with status {@code 400 (Bad Request)} if the membershipHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membership-histories")
    public ResponseEntity<MembershipHistory> updateMembershipHistory(@RequestBody MembershipHistory membershipHistory) throws URISyntaxException {
        log.debug("REST request to update MembershipHistory : {}", membershipHistory);
        if (membershipHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembershipHistory result = membershipHistoryService.save(membershipHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membership-histories} : get all the membershipHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipHistories in body.
     */
    @GetMapping("/membership-histories")
    public ResponseEntity<List<MembershipHistory>> getAllMembershipHistories(Pageable pageable) {
        log.debug("REST request to get a page of MembershipHistories");
        Page<MembershipHistory> page = membershipHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membership-histories/:id} : get the "id" membershipHistory.
     *
     * @param id the id of the membershipHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membership-histories/{id}")
    public ResponseEntity<MembershipHistory> getMembershipHistory(@PathVariable Long id) {
        log.debug("REST request to get MembershipHistory : {}", id);
        Optional<MembershipHistory> membershipHistory = membershipHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipHistory);
    }

    /**
     * {@code DELETE  /membership-histories/:id} : delete the "id" membershipHistory.
     *
     * @param id the id of the membershipHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membership-histories/{id}")
    public ResponseEntity<Void> deleteMembershipHistory(@PathVariable Long id) {
        log.debug("REST request to delete MembershipHistory : {}", id);
        membershipHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
