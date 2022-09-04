package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameSupport;
import com.iord.service.gameportal.repository.GameSupportRepository;
import com.iord.service.gameportal.service.GameSupportService;

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
 * Integration tests for the {@link GameSupportResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameSupportResourceIT {

    private static final String DEFAULT_GAME_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_GAME_QUESTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ACC_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ACC_QUESTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TECNICAL_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_TECNICAL_QUESTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_FINANCIAL_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_QUESTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_RULES_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_RULES_QUESTIONS = "BBBBBBBBBB";

    @Autowired
    private GameSupportRepository gameSupportRepository;

    @Autowired
    private GameSupportService gameSupportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameSupportMockMvc;

    private GameSupport gameSupport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSupport createEntity(EntityManager em) {
        GameSupport gameSupport = new GameSupport()
            .gameQuestions(DEFAULT_GAME_QUESTIONS)
            .accQuestions(DEFAULT_ACC_QUESTIONS)
            .tecnicalQuestions(DEFAULT_TECNICAL_QUESTIONS)
            .financialQuestions(DEFAULT_FINANCIAL_QUESTIONS)
            .rulesQuestions(DEFAULT_RULES_QUESTIONS);
        return gameSupport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSupport createUpdatedEntity(EntityManager em) {
        GameSupport gameSupport = new GameSupport()
            .gameQuestions(UPDATED_GAME_QUESTIONS)
            .accQuestions(UPDATED_ACC_QUESTIONS)
            .tecnicalQuestions(UPDATED_TECNICAL_QUESTIONS)
            .financialQuestions(UPDATED_FINANCIAL_QUESTIONS)
            .rulesQuestions(UPDATED_RULES_QUESTIONS);
        return gameSupport;
    }

    @BeforeEach
    public void initTest() {
        gameSupport = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameSupport() throws Exception {
        int databaseSizeBeforeCreate = gameSupportRepository.findAll().size();
        // Create the GameSupport
        restGameSupportMockMvc.perform(post("/api/game-supports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSupport)))
            .andExpect(status().isCreated());

        // Validate the GameSupport in the database
        List<GameSupport> gameSupportList = gameSupportRepository.findAll();
        assertThat(gameSupportList).hasSize(databaseSizeBeforeCreate + 1);
        GameSupport testGameSupport = gameSupportList.get(gameSupportList.size() - 1);
        assertThat(testGameSupport.getGameQuestions()).isEqualTo(DEFAULT_GAME_QUESTIONS);
        assertThat(testGameSupport.getAccQuestions()).isEqualTo(DEFAULT_ACC_QUESTIONS);
        assertThat(testGameSupport.getTecnicalQuestions()).isEqualTo(DEFAULT_TECNICAL_QUESTIONS);
        assertThat(testGameSupport.getFinancialQuestions()).isEqualTo(DEFAULT_FINANCIAL_QUESTIONS);
        assertThat(testGameSupport.getRulesQuestions()).isEqualTo(DEFAULT_RULES_QUESTIONS);
    }

    @Test
    @Transactional
    public void createGameSupportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameSupportRepository.findAll().size();

        // Create the GameSupport with an existing ID
        gameSupport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameSupportMockMvc.perform(post("/api/game-supports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSupport)))
            .andExpect(status().isBadRequest());

        // Validate the GameSupport in the database
        List<GameSupport> gameSupportList = gameSupportRepository.findAll();
        assertThat(gameSupportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameSupports() throws Exception {
        // Initialize the database
        gameSupportRepository.saveAndFlush(gameSupport);

        // Get all the gameSupportList
        restGameSupportMockMvc.perform(get("/api/game-supports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameSupport.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameQuestions").value(hasItem(DEFAULT_GAME_QUESTIONS)))
            .andExpect(jsonPath("$.[*].accQuestions").value(hasItem(DEFAULT_ACC_QUESTIONS)))
            .andExpect(jsonPath("$.[*].tecnicalQuestions").value(hasItem(DEFAULT_TECNICAL_QUESTIONS)))
            .andExpect(jsonPath("$.[*].financialQuestions").value(hasItem(DEFAULT_FINANCIAL_QUESTIONS)))
            .andExpect(jsonPath("$.[*].rulesQuestions").value(hasItem(DEFAULT_RULES_QUESTIONS)));
    }
    
    @Test
    @Transactional
    public void getGameSupport() throws Exception {
        // Initialize the database
        gameSupportRepository.saveAndFlush(gameSupport);

        // Get the gameSupport
        restGameSupportMockMvc.perform(get("/api/game-supports/{id}", gameSupport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameSupport.getId().intValue()))
            .andExpect(jsonPath("$.gameQuestions").value(DEFAULT_GAME_QUESTIONS))
            .andExpect(jsonPath("$.accQuestions").value(DEFAULT_ACC_QUESTIONS))
            .andExpect(jsonPath("$.tecnicalQuestions").value(DEFAULT_TECNICAL_QUESTIONS))
            .andExpect(jsonPath("$.financialQuestions").value(DEFAULT_FINANCIAL_QUESTIONS))
            .andExpect(jsonPath("$.rulesQuestions").value(DEFAULT_RULES_QUESTIONS));
    }
    @Test
    @Transactional
    public void getNonExistingGameSupport() throws Exception {
        // Get the gameSupport
        restGameSupportMockMvc.perform(get("/api/game-supports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameSupport() throws Exception {
        // Initialize the database
        gameSupportService.save(gameSupport);

        int databaseSizeBeforeUpdate = gameSupportRepository.findAll().size();

        // Update the gameSupport
        GameSupport updatedGameSupport = gameSupportRepository.findById(gameSupport.getId()).get();
        // Disconnect from session so that the updates on updatedGameSupport are not directly saved in db
        em.detach(updatedGameSupport);
        updatedGameSupport
            .gameQuestions(UPDATED_GAME_QUESTIONS)
            .accQuestions(UPDATED_ACC_QUESTIONS)
            .tecnicalQuestions(UPDATED_TECNICAL_QUESTIONS)
            .financialQuestions(UPDATED_FINANCIAL_QUESTIONS)
            .rulesQuestions(UPDATED_RULES_QUESTIONS);

        restGameSupportMockMvc.perform(put("/api/game-supports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameSupport)))
            .andExpect(status().isOk());

        // Validate the GameSupport in the database
        List<GameSupport> gameSupportList = gameSupportRepository.findAll();
        assertThat(gameSupportList).hasSize(databaseSizeBeforeUpdate);
        GameSupport testGameSupport = gameSupportList.get(gameSupportList.size() - 1);
        assertThat(testGameSupport.getGameQuestions()).isEqualTo(UPDATED_GAME_QUESTIONS);
        assertThat(testGameSupport.getAccQuestions()).isEqualTo(UPDATED_ACC_QUESTIONS);
        assertThat(testGameSupport.getTecnicalQuestions()).isEqualTo(UPDATED_TECNICAL_QUESTIONS);
        assertThat(testGameSupport.getFinancialQuestions()).isEqualTo(UPDATED_FINANCIAL_QUESTIONS);
        assertThat(testGameSupport.getRulesQuestions()).isEqualTo(UPDATED_RULES_QUESTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingGameSupport() throws Exception {
        int databaseSizeBeforeUpdate = gameSupportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameSupportMockMvc.perform(put("/api/game-supports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSupport)))
            .andExpect(status().isBadRequest());

        // Validate the GameSupport in the database
        List<GameSupport> gameSupportList = gameSupportRepository.findAll();
        assertThat(gameSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameSupport() throws Exception {
        // Initialize the database
        gameSupportService.save(gameSupport);

        int databaseSizeBeforeDelete = gameSupportRepository.findAll().size();

        // Delete the gameSupport
        restGameSupportMockMvc.perform(delete("/api/game-supports/{id}", gameSupport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameSupport> gameSupportList = gameSupportRepository.findAll();
        assertThat(gameSupportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
