package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GamePlatform;
import com.iord.service.gameportal.repository.GamePlatformRepository;
import com.iord.service.gameportal.service.GamePlatformService;

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
 * Integration tests for the {@link GamePlatformResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GamePlatformResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GamePlatformRepository gamePlatformRepository;

    @Autowired
    private GamePlatformService gamePlatformService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamePlatformMockMvc;

    private GamePlatform gamePlatform;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePlatform createEntity(EntityManager em) {
        GamePlatform gamePlatform = new GamePlatform()
            .name(DEFAULT_NAME);
        return gamePlatform;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GamePlatform createUpdatedEntity(EntityManager em) {
        GamePlatform gamePlatform = new GamePlatform()
            .name(UPDATED_NAME);
        return gamePlatform;
    }

    @BeforeEach
    public void initTest() {
        gamePlatform = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamePlatform() throws Exception {
        int databaseSizeBeforeCreate = gamePlatformRepository.findAll().size();
        // Create the GamePlatform
        restGamePlatformMockMvc.perform(post("/api/game-platforms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlatform)))
            .andExpect(status().isCreated());

        // Validate the GamePlatform in the database
        List<GamePlatform> gamePlatformList = gamePlatformRepository.findAll();
        assertThat(gamePlatformList).hasSize(databaseSizeBeforeCreate + 1);
        GamePlatform testGamePlatform = gamePlatformList.get(gamePlatformList.size() - 1);
        assertThat(testGamePlatform.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGamePlatformWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamePlatformRepository.findAll().size();

        // Create the GamePlatform with an existing ID
        gamePlatform.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamePlatformMockMvc.perform(post("/api/game-platforms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlatform)))
            .andExpect(status().isBadRequest());

        // Validate the GamePlatform in the database
        List<GamePlatform> gamePlatformList = gamePlatformRepository.findAll();
        assertThat(gamePlatformList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGamePlatforms() throws Exception {
        // Initialize the database
        gamePlatformRepository.saveAndFlush(gamePlatform);

        // Get all the gamePlatformList
        restGamePlatformMockMvc.perform(get("/api/game-platforms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamePlatform.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGamePlatform() throws Exception {
        // Initialize the database
        gamePlatformRepository.saveAndFlush(gamePlatform);

        // Get the gamePlatform
        restGamePlatformMockMvc.perform(get("/api/game-platforms/{id}", gamePlatform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gamePlatform.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGamePlatform() throws Exception {
        // Get the gamePlatform
        restGamePlatformMockMvc.perform(get("/api/game-platforms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamePlatform() throws Exception {
        // Initialize the database
        gamePlatformService.save(gamePlatform);

        int databaseSizeBeforeUpdate = gamePlatformRepository.findAll().size();

        // Update the gamePlatform
        GamePlatform updatedGamePlatform = gamePlatformRepository.findById(gamePlatform.getId()).get();
        // Disconnect from session so that the updates on updatedGamePlatform are not directly saved in db
        em.detach(updatedGamePlatform);
        updatedGamePlatform
            .name(UPDATED_NAME);

        restGamePlatformMockMvc.perform(put("/api/game-platforms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGamePlatform)))
            .andExpect(status().isOk());

        // Validate the GamePlatform in the database
        List<GamePlatform> gamePlatformList = gamePlatformRepository.findAll();
        assertThat(gamePlatformList).hasSize(databaseSizeBeforeUpdate);
        GamePlatform testGamePlatform = gamePlatformList.get(gamePlatformList.size() - 1);
        assertThat(testGamePlatform.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGamePlatform() throws Exception {
        int databaseSizeBeforeUpdate = gamePlatformRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamePlatformMockMvc.perform(put("/api/game-platforms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gamePlatform)))
            .andExpect(status().isBadRequest());

        // Validate the GamePlatform in the database
        List<GamePlatform> gamePlatformList = gamePlatformRepository.findAll();
        assertThat(gamePlatformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGamePlatform() throws Exception {
        // Initialize the database
        gamePlatformService.save(gamePlatform);

        int databaseSizeBeforeDelete = gamePlatformRepository.findAll().size();

        // Delete the gamePlatform
        restGamePlatformMockMvc.perform(delete("/api/game-platforms/{id}", gamePlatform.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GamePlatform> gamePlatformList = gamePlatformRepository.findAll();
        assertThat(gamePlatformList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
