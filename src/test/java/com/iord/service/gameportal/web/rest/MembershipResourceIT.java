package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.Membership;
import com.iord.service.gameportal.repository.MembershipRepository;
import com.iord.service.gameportal.service.MembershipService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MembershipResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembershipResourceIT {

    private static final Long DEFAULT_MEMBERID = 1L;
    private static final Long UPDATED_MEMBERID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GAME_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TITLE = "BBBBBBBBBB";

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipMockMvc;

    private Membership membership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createEntity(EntityManager em) {
        Membership membership = new Membership()
            .memberid(DEFAULT_MEMBERID)
            .name(DEFAULT_NAME)
            .gameTitle(DEFAULT_GAME_TITLE);
        return membership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createUpdatedEntity(EntityManager em) {
        Membership membership = new Membership()
            .memberid(UPDATED_MEMBERID)
            .name(UPDATED_NAME)
            .gameTitle(UPDATED_GAME_TITLE);
        return membership;
    }

    @BeforeEach
    public void initTest() {
        membership = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembership() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();
        // Create the Membership
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isCreated());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate + 1);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getMemberid()).isEqualTo(DEFAULT_MEMBERID);
        assertThat(testMembership.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMembership.getGameTitle()).isEqualTo(DEFAULT_GAME_TITLE);
    }

    @Test
    @Transactional
    public void createMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();

        // Create the Membership with an existing ID
        membership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMemberships() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get all the membershipList
        restMembershipMockMvc.perform(get("/api/memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membership.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberid").value(hasItem(DEFAULT_MEMBERID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gameTitle").value(hasItem(DEFAULT_GAME_TITLE)));
    }
    
    @Test
    @Transactional
    public void getMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", membership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membership.getId().intValue()))
            .andExpect(jsonPath("$.memberid").value(DEFAULT_MEMBERID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gameTitle").value(DEFAULT_GAME_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingMembership() throws Exception {
        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembership() throws Exception {
        // Initialize the database
        membershipService.save(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership
        Membership updatedMembership = membershipRepository.findById(membership.getId()).get();
        // Disconnect from session so that the updates on updatedMembership are not directly saved in db
        em.detach(updatedMembership);
        updatedMembership
            .memberid(UPDATED_MEMBERID)
            .name(UPDATED_NAME)
            .gameTitle(UPDATED_GAME_TITLE);

        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembership)))
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getMemberid()).isEqualTo(UPDATED_MEMBERID);
        assertThat(testMembership.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMembership.getGameTitle()).isEqualTo(UPDATED_GAME_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembership() throws Exception {
        // Initialize the database
        membershipService.save(membership);

        int databaseSizeBeforeDelete = membershipRepository.findAll().size();

        // Delete the membership
        restMembershipMockMvc.perform(delete("/api/memberships/{id}", membership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
