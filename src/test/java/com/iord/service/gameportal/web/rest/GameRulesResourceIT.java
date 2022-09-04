package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameRules;
import com.iord.service.gameportal.repository.GameRulesRepository;
import com.iord.service.gameportal.service.GameRulesService;

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
 * Integration tests for the {@link GameRulesResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameRulesResourceIT {

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    @Autowired
    private GameRulesRepository gameRulesRepository;

    @Autowired
    private GameRulesService gameRulesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameRulesMockMvc;

    private GameRules gameRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameRules createEntity(EntityManager em) {
        GameRules gameRules = new GameRules()
            .ruleName(DEFAULT_RULE_NAME)
            .description(DEFAULT_DESCRIPTION)
            .defaultValue(DEFAULT_DEFAULT_VALUE);
        return gameRules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameRules createUpdatedEntity(EntityManager em) {
        GameRules gameRules = new GameRules()
            .ruleName(UPDATED_RULE_NAME)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE);
        return gameRules;
    }

    @BeforeEach
    public void initTest() {
        gameRules = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameRules() throws Exception {
        int databaseSizeBeforeCreate = gameRulesRepository.findAll().size();
        // Create the GameRules
        restGameRulesMockMvc.perform(post("/api/game-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRules)))
            .andExpect(status().isCreated());

        // Validate the GameRules in the database
        List<GameRules> gameRulesList = gameRulesRepository.findAll();
        assertThat(gameRulesList).hasSize(databaseSizeBeforeCreate + 1);
        GameRules testGameRules = gameRulesList.get(gameRulesList.size() - 1);
        assertThat(testGameRules.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testGameRules.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGameRules.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createGameRulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRulesRepository.findAll().size();

        // Create the GameRules with an existing ID
        gameRules.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameRulesMockMvc.perform(post("/api/game-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRules)))
            .andExpect(status().isBadRequest());

        // Validate the GameRules in the database
        List<GameRules> gameRulesList = gameRulesRepository.findAll();
        assertThat(gameRulesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameRules() throws Exception {
        // Initialize the database
        gameRulesRepository.saveAndFlush(gameRules);

        // Get all the gameRulesList
        restGameRulesMockMvc.perform(get("/api/game-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getGameRules() throws Exception {
        // Initialize the database
        gameRulesRepository.saveAndFlush(gameRules);

        // Get the gameRules
        restGameRulesMockMvc.perform(get("/api/game-rules/{id}", gameRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameRules.getId().intValue()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingGameRules() throws Exception {
        // Get the gameRules
        restGameRulesMockMvc.perform(get("/api/game-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameRules() throws Exception {
        // Initialize the database
        gameRulesService.save(gameRules);

        int databaseSizeBeforeUpdate = gameRulesRepository.findAll().size();

        // Update the gameRules
        GameRules updatedGameRules = gameRulesRepository.findById(gameRules.getId()).get();
        // Disconnect from session so that the updates on updatedGameRules are not directly saved in db
        em.detach(updatedGameRules);
        updatedGameRules
            .ruleName(UPDATED_RULE_NAME)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE);

        restGameRulesMockMvc.perform(put("/api/game-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameRules)))
            .andExpect(status().isOk());

        // Validate the GameRules in the database
        List<GameRules> gameRulesList = gameRulesRepository.findAll();
        assertThat(gameRulesList).hasSize(databaseSizeBeforeUpdate);
        GameRules testGameRules = gameRulesList.get(gameRulesList.size() - 1);
        assertThat(testGameRules.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testGameRules.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGameRules.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingGameRules() throws Exception {
        int databaseSizeBeforeUpdate = gameRulesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameRulesMockMvc.perform(put("/api/game-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRules)))
            .andExpect(status().isBadRequest());

        // Validate the GameRules in the database
        List<GameRules> gameRulesList = gameRulesRepository.findAll();
        assertThat(gameRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameRules() throws Exception {
        // Initialize the database
        gameRulesService.save(gameRules);

        int databaseSizeBeforeDelete = gameRulesRepository.findAll().size();

        // Delete the gameRules
        restGameRulesMockMvc.perform(delete("/api/game-rules/{id}", gameRules.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameRules> gameRulesList = gameRulesRepository.findAll();
        assertThat(gameRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
