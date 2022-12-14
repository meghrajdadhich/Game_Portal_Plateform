package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GamePlan;
import com.iord.service.gameportal.repository.GamePlanRepository;
import com.iord.service.gameportal.service.GamePlanService;

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
 * Integration tests for the {@link GamePlanResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GamePlanResourceIT {

    private static final String DEFAULT_PLAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_NAME = "BBBBBBBBBB";

    @Autowired
    private GamePlanRepository gamePlanRepository;

    @Autowired
    private GamePlanService gamePlanService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamePlanMockMvc;

    private GamePlan gamePlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePlan createEntity(EntityManager em) {
        GamePlan gamePlan = new GamePlan()
            .planName(DEFAULT_PLAN_NAME);
        return gamePlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePlan createUpdatedEntity(EntityManager em) {
        GamePlan gamePlan = new GamePlan()
            .planName(UPDATED_PLAN_NAME);
        return gamePlan;
    }

    @BeforeEach
    public void initTest() {
        gamePlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamePlan() throws Exception {
        int databaseSizeBeforeCreate = gamePlanRepository.findAll().size();
        // Create the GamePlan
        restGamePlanMockMvc.perform(post("/api/game-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlan)))
            .andExpect(status().isCreated());

        // Validate the GamePlan in the database
        List<GamePlan> gamePlanList = gamePlanRepository.findAll();
        assertThat(gamePlanList).hasSize(databaseSizeBeforeCreate + 1);
        GamePlan testGamePlan = gamePlanList.get(gamePlanList.size() - 1);
        assertThat(testGamePlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
    }

    @Test
    @Transactional
    public void createGamePlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamePlanRepository.findAll().size();

        // Create the GamePlan with an existing ID
        gamePlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamePlanMockMvc.perform(post("/api/game-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlan)))
            .andExpect(status().isBadRequest());

        // Validate the GamePlan in the database
        List<GamePlan> gamePlanList = gamePlanRepository.findAll();
        assertThat(gamePlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGamePlans() throws Exception {
        // Initialize the database
        gamePlanRepository.saveAndFlush(gamePlan);

        // Get all the gamePlanList
        restGamePlanMockMvc.perform(get("/api/game-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME)));
    }
    
    @Test
    @Transactional
    public void getGamePlan() throws Exception {
        // Initialize the database
        gamePlanRepository.saveAndFlush(gamePlan);

        // Get the gamePlan
        restGamePlanMockMvc.perform(get("/api/game-plans/{id}", gamePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gamePlan.getId().intValue()))
            .andExpect(jsonPath("$.planName").value(DEFAULT_PLAN_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGamePlan() throws Exception {
        // Get the gamePlan
        restGamePlanMockMvc.perform(get("/api/game-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamePlan() throws Exception {
        // Initialize the database
        gamePlanService.save(gamePlan);

        int databaseSizeBeforeUpdate = gamePlanRepository.findAll().size();

        // Update the gamePlan
        GamePlan updatedGamePlan = gamePlanRepository.findById(gamePlan.getId()).get();
        // Disconnect from session so that the updates on updatedGamePlan are not directly saved in db
        em.detach(updatedGamePlan);
        updatedGamePlan
            .planName(UPDATED_PLAN_NAME);

        restGamePlanMockMvc.perform(put("/api/game-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGamePlan)))
            .andExpect(status().isOk());

        // Validate the GamePlan in the database
        List<GamePlan> gamePlanList = gamePlanRepository.findAll();
        assertThat(gamePlanList).hasSize(databaseSizeBeforeUpdate);
        GamePlan testGamePlan = gamePlanList.get(gamePlanList.size() - 1);
        assertThat(testGamePlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGamePlan() throws Exception {
        int databaseSizeBeforeUpdate = gamePlanRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamePlanMockMvc.perform(put("/api/game-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlan)))
            .andExpect(status().isBadRequest());

        // Validate the GamePlan in the database
        List<GamePlan> gamePlanList = gamePlanRepository.findAll();
        assertThat(gamePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGamePlan() throws Exception {
        // Initialize the database
        gamePlanService.save(gamePlan);

        int databaseSizeBeforeDelete = gamePlanRepository.findAll().size();

        // Delete the gamePlan
        restGamePlanMockMvc.perform(delete("/api/game-plans/{id}", gamePlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GamePlan> gamePlanList = gamePlanRepository.findAll();
        assertThat(gamePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
