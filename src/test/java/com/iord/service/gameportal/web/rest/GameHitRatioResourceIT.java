package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameHitRatio;
import com.iord.service.gameportal.repository.GameHitRatioRepository;
import com.iord.service.gameportal.service.GameHitRatioService;

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
 * Integration tests for the {@link GameHitRatioResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameHitRatioResourceIT {

    private static final Long DEFAULT_NUMBER_OFHITS = 1L;
    private static final Long UPDATED_NUMBER_OFHITS = 2L;

    private static final Long DEFAULT_SCORE = 1L;
    private static final Long UPDATED_SCORE = 2L;

    @Autowired
    private GameHitRatioRepository gameHitRatioRepository;

    @Autowired
    private GameHitRatioService gameHitRatioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameHitRatioMockMvc;

    private GameHitRatio gameHitRatio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameHitRatio createEntity(EntityManager em) {
        GameHitRatio gameHitRatio = new GameHitRatio()
            .numberOfhits(DEFAULT_NUMBER_OFHITS)
            .score(DEFAULT_SCORE);
        return gameHitRatio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameHitRatio createUpdatedEntity(EntityManager em) {
        GameHitRatio gameHitRatio = new GameHitRatio()
            .numberOfhits(UPDATED_NUMBER_OFHITS)
            .score(UPDATED_SCORE);
        return gameHitRatio;
    }

    @BeforeEach
    public void initTest() {
        gameHitRatio = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameHitRatio() throws Exception {
        int databaseSizeBeforeCreate = gameHitRatioRepository.findAll().size();
        // Create the GameHitRatio
        restGameHitRatioMockMvc.perform(post("/api/game-hit-ratios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameHitRatio)))
            .andExpect(status().isCreated());

        // Validate the GameHitRatio in the database
        List<GameHitRatio> gameHitRatioList = gameHitRatioRepository.findAll();
        assertThat(gameHitRatioList).hasSize(databaseSizeBeforeCreate + 1);
        GameHitRatio testGameHitRatio = gameHitRatioList.get(gameHitRatioList.size() - 1);
        assertThat(testGameHitRatio.getNumberOfhits()).isEqualTo(DEFAULT_NUMBER_OFHITS);
        assertThat(testGameHitRatio.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createGameHitRatioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameHitRatioRepository.findAll().size();

        // Create the GameHitRatio with an existing ID
        gameHitRatio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameHitRatioMockMvc.perform(post("/api/game-hit-ratios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameHitRatio)))
            .andExpect(status().isBadRequest());

        // Validate the GameHitRatio in the database
        List<GameHitRatio> gameHitRatioList = gameHitRatioRepository.findAll();
        assertThat(gameHitRatioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameHitRatios() throws Exception {
        // Initialize the database
        gameHitRatioRepository.saveAndFlush(gameHitRatio);

        // Get all the gameHitRatioList
        restGameHitRatioMockMvc.perform(get("/api/game-hit-ratios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameHitRatio.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfhits").value(hasItem(DEFAULT_NUMBER_OFHITS.intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())));
    }
    
    @Test
    @Transactional
    public void getGameHitRatio() throws Exception {
        // Initialize the database
        gameHitRatioRepository.saveAndFlush(gameHitRatio);

        // Get the gameHitRatio
        restGameHitRatioMockMvc.perform(get("/api/game-hit-ratios/{id}", gameHitRatio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameHitRatio.getId().intValue()))
            .andExpect(jsonPath("$.numberOfhits").value(DEFAULT_NUMBER_OFHITS.intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGameHitRatio() throws Exception {
        // Get the gameHitRatio
        restGameHitRatioMockMvc.perform(get("/api/game-hit-ratios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameHitRatio() throws Exception {
        // Initialize the database
        gameHitRatioService.save(gameHitRatio);

        int databaseSizeBeforeUpdate = gameHitRatioRepository.findAll().size();

        // Update the gameHitRatio
        GameHitRatio updatedGameHitRatio = gameHitRatioRepository.findById(gameHitRatio.getId()).get();
        // Disconnect from session so that the updates on updatedGameHitRatio are not directly saved in db
        em.detach(updatedGameHitRatio);
        updatedGameHitRatio
            .numberOfhits(UPDATED_NUMBER_OFHITS)
            .score(UPDATED_SCORE);

        restGameHitRatioMockMvc.perform(put("/api/game-hit-ratios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameHitRatio)))
            .andExpect(status().isOk());

        // Validate the GameHitRatio in the database
        List<GameHitRatio> gameHitRatioList = gameHitRatioRepository.findAll();
        assertThat(gameHitRatioList).hasSize(databaseSizeBeforeUpdate);
        GameHitRatio testGameHitRatio = gameHitRatioList.get(gameHitRatioList.size() - 1);
        assertThat(testGameHitRatio.getNumberOfhits()).isEqualTo(UPDATED_NUMBER_OFHITS);
        assertThat(testGameHitRatio.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingGameHitRatio() throws Exception {
        int databaseSizeBeforeUpdate = gameHitRatioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameHitRatioMockMvc.perform(put("/api/game-hit-ratios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameHitRatio)))
            .andExpect(status().isBadRequest());

        // Validate the GameHitRatio in the database
        List<GameHitRatio> gameHitRatioList = gameHitRatioRepository.findAll();
        assertThat(gameHitRatioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameHitRatio() throws Exception {
        // Initialize the database
        gameHitRatioService.save(gameHitRatio);

        int databaseSizeBeforeDelete = gameHitRatioRepository.findAll().size();

        // Delete the gameHitRatio
        restGameHitRatioMockMvc.perform(delete("/api/game-hit-ratios/{id}", gameHitRatio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameHitRatio> gameHitRatioList = gameHitRatioRepository.findAll();
        assertThat(gameHitRatioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
