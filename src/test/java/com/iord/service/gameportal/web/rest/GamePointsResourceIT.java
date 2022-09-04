package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GamePoints;
import com.iord.service.gameportal.repository.GamePointsRepository;
import com.iord.service.gameportal.service.GamePointsService;

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
 * Integration tests for the {@link GamePointsResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GamePointsResourceIT {

    private static final Long DEFAULT_PID = 1L;
    private static final Long UPDATED_PID = 2L;

    private static final Long DEFAULT_TOTAL_POINTS = 1L;
    private static final Long UPDATED_TOTAL_POINTS = 2L;

    @Autowired
    private GamePointsRepository gamePointsRepository;

    @Autowired
    private GamePointsService gamePointsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamePointsMockMvc;

    private GamePoints gamePoints;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePoints createEntity(EntityManager em) {
        GamePoints gamePoints = new GamePoints()
            .pid(DEFAULT_PID)
            .totalPoints(DEFAULT_TOTAL_POINTS);
        return gamePoints;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePoints createUpdatedEntity(EntityManager em) {
        GamePoints gamePoints = new GamePoints()
            .pid(UPDATED_PID)
            .totalPoints(UPDATED_TOTAL_POINTS);
        return gamePoints;
    }

    @BeforeEach
    public void initTest() {
        gamePoints = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamePoints() throws Exception {
        int databaseSizeBeforeCreate = gamePointsRepository.findAll().size();
        // Create the GamePoints
        restGamePointsMockMvc.perform(post("/api/game-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isCreated());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeCreate + 1);
        GamePoints testGamePoints = gamePointsList.get(gamePointsList.size() - 1);
        assertThat(testGamePoints.getPid()).isEqualTo(DEFAULT_PID);
        assertThat(testGamePoints.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
    }

    @Test
    @Transactional
    public void createGamePointsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamePointsRepository.findAll().size();

        // Create the GamePoints with an existing ID
        gamePoints.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamePointsMockMvc.perform(post("/api/game-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isBadRequest());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);

        // Get all the gamePointsList
        restGamePointsMockMvc.perform(get("/api/game-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamePoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID.intValue())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS.intValue())));
    }
    
    @Test
    @Transactional
    public void getGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);

        // Get the gamePoints
        restGamePointsMockMvc.perform(get("/api/game-points/{id}", gamePoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gamePoints.getId().intValue()))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID.intValue()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGamePoints() throws Exception {
        // Get the gamePoints
        restGamePointsMockMvc.perform(get("/api/game-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamePoints() throws Exception {
        // Initialize the database
        gamePointsService.save(gamePoints);

        int databaseSizeBeforeUpdate = gamePointsRepository.findAll().size();

        // Update the gamePoints
        GamePoints updatedGamePoints = gamePointsRepository.findById(gamePoints.getId()).get();
        // Disconnect from session so that the updates on updatedGamePoints are not directly saved in db
        em.detach(updatedGamePoints);
        updatedGamePoints
            .pid(UPDATED_PID)
            .totalPoints(UPDATED_TOTAL_POINTS);

        restGamePointsMockMvc.perform(put("/api/game-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGamePoints)))
            .andExpect(status().isOk());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeUpdate);
        GamePoints testGamePoints = gamePointsList.get(gamePointsList.size() - 1);
        assertThat(testGamePoints.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testGamePoints.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingGamePoints() throws Exception {
        int databaseSizeBeforeUpdate = gamePointsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamePointsMockMvc.perform(put("/api/game-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isBadRequest());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGamePoints() throws Exception {
        // Initialize the database
        gamePointsService.save(gamePoints);

        int databaseSizeBeforeDelete = gamePointsRepository.findAll().size();

        // Delete the gamePoints
        restGamePointsMockMvc.perform(delete("/api/game-points/{id}", gamePoints.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
