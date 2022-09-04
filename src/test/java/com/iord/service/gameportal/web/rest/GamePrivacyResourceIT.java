package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GamePrivacy;
import com.iord.service.gameportal.repository.GamePrivacyRepository;
import com.iord.service.gameportal.service.GamePrivacyService;

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
 * Integration tests for the {@link GamePrivacyResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GamePrivacyResourceIT {

    private static final String DEFAULT_AGREEMENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONALINFO = "AAAAAAAAAA";
    private static final String UPDATED_PERSONALINFO = "BBBBBBBBBB";

    @Autowired
    private GamePrivacyRepository gamePrivacyRepository;

    @Autowired
    private GamePrivacyService gamePrivacyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamePrivacyMockMvc;

    private GamePrivacy gamePrivacy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePrivacy createEntity(EntityManager em) {
        GamePrivacy gamePrivacy = new GamePrivacy()
            .agreementDetails(DEFAULT_AGREEMENT_DETAILS)
            .personalinfo(DEFAULT_PERSONALINFO);
        return gamePrivacy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePrivacy createUpdatedEntity(EntityManager em) {
        GamePrivacy gamePrivacy = new GamePrivacy()
            .agreementDetails(UPDATED_AGREEMENT_DETAILS)
            .personalinfo(UPDATED_PERSONALINFO);
        return gamePrivacy;
    }

    @BeforeEach
    public void initTest() {
        gamePrivacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamePrivacy() throws Exception {
        int databaseSizeBeforeCreate = gamePrivacyRepository.findAll().size();
        // Create the GamePrivacy
        restGamePrivacyMockMvc.perform(post("/api/game-privacies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePrivacy)))
            .andExpect(status().isCreated());

        // Validate the GamePrivacy in the database
        List<GamePrivacy> gamePrivacyList = gamePrivacyRepository.findAll();
        assertThat(gamePrivacyList).hasSize(databaseSizeBeforeCreate + 1);
        GamePrivacy testGamePrivacy = gamePrivacyList.get(gamePrivacyList.size() - 1);
        assertThat(testGamePrivacy.getAgreementDetails()).isEqualTo(DEFAULT_AGREEMENT_DETAILS);
        assertThat(testGamePrivacy.getPersonalinfo()).isEqualTo(DEFAULT_PERSONALINFO);
    }

    @Test
    @Transactional
    public void createGamePrivacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamePrivacyRepository.findAll().size();

        // Create the GamePrivacy with an existing ID
        gamePrivacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamePrivacyMockMvc.perform(post("/api/game-privacies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePrivacy)))
            .andExpect(status().isBadRequest());

        // Validate the GamePrivacy in the database
        List<GamePrivacy> gamePrivacyList = gamePrivacyRepository.findAll();
        assertThat(gamePrivacyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGamePrivacies() throws Exception {
        // Initialize the database
        gamePrivacyRepository.saveAndFlush(gamePrivacy);

        // Get all the gamePrivacyList
        restGamePrivacyMockMvc.perform(get("/api/game-privacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamePrivacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreementDetails").value(hasItem(DEFAULT_AGREEMENT_DETAILS)))
            .andExpect(jsonPath("$.[*].personalinfo").value(hasItem(DEFAULT_PERSONALINFO)));
    }
    
    @Test
    @Transactional
    public void getGamePrivacy() throws Exception {
        // Initialize the database
        gamePrivacyRepository.saveAndFlush(gamePrivacy);

        // Get the gamePrivacy
        restGamePrivacyMockMvc.perform(get("/api/game-privacies/{id}", gamePrivacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gamePrivacy.getId().intValue()))
            .andExpect(jsonPath("$.agreementDetails").value(DEFAULT_AGREEMENT_DETAILS))
            .andExpect(jsonPath("$.personalinfo").value(DEFAULT_PERSONALINFO));
    }
    @Test
    @Transactional
    public void getNonExistingGamePrivacy() throws Exception {
        // Get the gamePrivacy
        restGamePrivacyMockMvc.perform(get("/api/game-privacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamePrivacy() throws Exception {
        // Initialize the database
        gamePrivacyService.save(gamePrivacy);

        int databaseSizeBeforeUpdate = gamePrivacyRepository.findAll().size();

        // Update the gamePrivacy
        GamePrivacy updatedGamePrivacy = gamePrivacyRepository.findById(gamePrivacy.getId()).get();
        // Disconnect from session so that the updates on updatedGamePrivacy are not directly saved in db
        em.detach(updatedGamePrivacy);
        updatedGamePrivacy
            .agreementDetails(UPDATED_AGREEMENT_DETAILS)
            .personalinfo(UPDATED_PERSONALINFO);

        restGamePrivacyMockMvc.perform(put("/api/game-privacies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGamePrivacy)))
            .andExpect(status().isOk());

        // Validate the GamePrivacy in the database
        List<GamePrivacy> gamePrivacyList = gamePrivacyRepository.findAll();
        assertThat(gamePrivacyList).hasSize(databaseSizeBeforeUpdate);
        GamePrivacy testGamePrivacy = gamePrivacyList.get(gamePrivacyList.size() - 1);
        assertThat(testGamePrivacy.getAgreementDetails()).isEqualTo(UPDATED_AGREEMENT_DETAILS);
        assertThat(testGamePrivacy.getPersonalinfo()).isEqualTo(UPDATED_PERSONALINFO);
    }

    @Test
    @Transactional
    public void updateNonExistingGamePrivacy() throws Exception {
        int databaseSizeBeforeUpdate = gamePrivacyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamePrivacyMockMvc.perform(put("/api/game-privacies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePrivacy)))
            .andExpect(status().isBadRequest());

        // Validate the GamePrivacy in the database
        List<GamePrivacy> gamePrivacyList = gamePrivacyRepository.findAll();
        assertThat(gamePrivacyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGamePrivacy() throws Exception {
        // Initialize the database
        gamePrivacyService.save(gamePrivacy);

        int databaseSizeBeforeDelete = gamePrivacyRepository.findAll().size();

        // Delete the gamePrivacy
        restGamePrivacyMockMvc.perform(delete("/api/game-privacies/{id}", gamePrivacy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GamePrivacy> gamePrivacyList = gamePrivacyRepository.findAll();
        assertThat(gamePrivacyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
