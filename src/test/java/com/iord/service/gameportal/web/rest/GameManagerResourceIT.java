package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameManager;
import com.iord.service.gameportal.repository.GameManagerRepository;
import com.iord.service.gameportal.service.GameManagerService;

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
 * Integration tests for the {@link GameManagerResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameManagerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;

    @Autowired
    private GameManagerRepository gameManagerRepository;

    @Autowired
    private GameManagerService gameManagerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameManagerMockMvc;

    private GameManager gameManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameManager createEntity(EntityManager em) {
        GameManager gameManager = new GameManager()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return gameManager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameManager createUpdatedEntity(EntityManager em) {
        GameManager gameManager = new GameManager()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        return gameManager;
    }

    @BeforeEach
    public void initTest() {
        gameManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameManager() throws Exception {
        int databaseSizeBeforeCreate = gameManagerRepository.findAll().size();
        // Create the GameManager
        restGameManagerMockMvc.perform(post("/api/game-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameManager)))
            .andExpect(status().isCreated());

        // Validate the GameManager in the database
        List<GameManager> gameManagerList = gameManagerRepository.findAll();
        assertThat(gameManagerList).hasSize(databaseSizeBeforeCreate + 1);
        GameManager testGameManager = gameManagerList.get(gameManagerList.size() - 1);
        assertThat(testGameManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGameManager.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testGameManager.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void createGameManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameManagerRepository.findAll().size();

        // Create the GameManager with an existing ID
        gameManager.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameManagerMockMvc.perform(post("/api/game-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameManager)))
            .andExpect(status().isBadRequest());

        // Validate the GameManager in the database
        List<GameManager> gameManagerList = gameManagerRepository.findAll();
        assertThat(gameManagerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameManagers() throws Exception {
        // Initialize the database
        gameManagerRepository.saveAndFlush(gameManager);

        // Get all the gameManagerList
        restGameManagerMockMvc.perform(get("/api/game-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())));
    }
    
    @Test
    @Transactional
    public void getGameManager() throws Exception {
        // Initialize the database
        gameManagerRepository.saveAndFlush(gameManager);

        // Get the gameManager
        restGameManagerMockMvc.perform(get("/api/game-managers/{id}", gameManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameManager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGameManager() throws Exception {
        // Get the gameManager
        restGameManagerMockMvc.perform(get("/api/game-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameManager() throws Exception {
        // Initialize the database
        gameManagerService.save(gameManager);

        int databaseSizeBeforeUpdate = gameManagerRepository.findAll().size();

        // Update the gameManager
        GameManager updatedGameManager = gameManagerRepository.findById(gameManager.getId()).get();
        // Disconnect from session so that the updates on updatedGameManager are not directly saved in db
        em.detach(updatedGameManager);
        updatedGameManager
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER);

        restGameManagerMockMvc.perform(put("/api/game-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameManager)))
            .andExpect(status().isOk());

        // Validate the GameManager in the database
        List<GameManager> gameManagerList = gameManagerRepository.findAll();
        assertThat(gameManagerList).hasSize(databaseSizeBeforeUpdate);
        GameManager testGameManager = gameManagerList.get(gameManagerList.size() - 1);
        assertThat(testGameManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGameManager.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGameManager.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingGameManager() throws Exception {
        int databaseSizeBeforeUpdate = gameManagerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameManagerMockMvc.perform(put("/api/game-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameManager)))
            .andExpect(status().isBadRequest());

        // Validate the GameManager in the database
        List<GameManager> gameManagerList = gameManagerRepository.findAll();
        assertThat(gameManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameManager() throws Exception {
        // Initialize the database
        gameManagerService.save(gameManager);

        int databaseSizeBeforeDelete = gameManagerRepository.findAll().size();

        // Delete the gameManager
        restGameManagerMockMvc.perform(delete("/api/game-managers/{id}", gameManager.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameManager> gameManagerList = gameManagerRepository.findAll();
        assertThat(gameManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
