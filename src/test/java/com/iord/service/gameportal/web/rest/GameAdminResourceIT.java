package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameAdmin;
import com.iord.service.gameportal.repository.GameAdminRepository;
import com.iord.service.gameportal.service.GameAdminService;

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
 * Integration tests for the {@link GameAdminResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameAdminResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GameAdminRepository gameAdminRepository;

    @Autowired
    private GameAdminService gameAdminService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameAdminMockMvc;

    private GameAdmin gameAdmin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameAdmin createEntity(EntityManager em) {
        GameAdmin gameAdmin = new GameAdmin()
            .name(DEFAULT_NAME);
        return gameAdmin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameAdmin createUpdatedEntity(EntityManager em) {
        GameAdmin gameAdmin = new GameAdmin()
            .name(UPDATED_NAME);
        return gameAdmin;
    }

    @BeforeEach
    public void initTest() {
        gameAdmin = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameAdmin() throws Exception {
        int databaseSizeBeforeCreate = gameAdminRepository.findAll().size();
        // Create the GameAdmin
        restGameAdminMockMvc.perform(post("/api/game-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameAdmin)))
            .andExpect(status().isCreated());

        // Validate the GameAdmin in the database
        List<GameAdmin> gameAdminList = gameAdminRepository.findAll();
        assertThat(gameAdminList).hasSize(databaseSizeBeforeCreate + 1);
        GameAdmin testGameAdmin = gameAdminList.get(gameAdminList.size() - 1);
        assertThat(testGameAdmin.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGameAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameAdminRepository.findAll().size();

        // Create the GameAdmin with an existing ID
        gameAdmin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameAdminMockMvc.perform(post("/api/game-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the GameAdmin in the database
        List<GameAdmin> gameAdminList = gameAdminRepository.findAll();
        assertThat(gameAdminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameAdmins() throws Exception {
        // Initialize the database
        gameAdminRepository.saveAndFlush(gameAdmin);

        // Get all the gameAdminList
        restGameAdminMockMvc.perform(get("/api/game-admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameAdmin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGameAdmin() throws Exception {
        // Initialize the database
        gameAdminRepository.saveAndFlush(gameAdmin);

        // Get the gameAdmin
        restGameAdminMockMvc.perform(get("/api/game-admins/{id}", gameAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameAdmin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGameAdmin() throws Exception {
        // Get the gameAdmin
        restGameAdminMockMvc.perform(get("/api/game-admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameAdmin() throws Exception {
        // Initialize the database
        gameAdminService.save(gameAdmin);

        int databaseSizeBeforeUpdate = gameAdminRepository.findAll().size();

        // Update the gameAdmin
        GameAdmin updatedGameAdmin = gameAdminRepository.findById(gameAdmin.getId()).get();
        // Disconnect from session so that the updates on updatedGameAdmin are not directly saved in db
        em.detach(updatedGameAdmin);
        updatedGameAdmin
            .name(UPDATED_NAME);

        restGameAdminMockMvc.perform(put("/api/game-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameAdmin)))
            .andExpect(status().isOk());

        // Validate the GameAdmin in the database
        List<GameAdmin> gameAdminList = gameAdminRepository.findAll();
        assertThat(gameAdminList).hasSize(databaseSizeBeforeUpdate);
        GameAdmin testGameAdmin = gameAdminList.get(gameAdminList.size() - 1);
        assertThat(testGameAdmin.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGameAdmin() throws Exception {
        int databaseSizeBeforeUpdate = gameAdminRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameAdminMockMvc.perform(put("/api/game-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the GameAdmin in the database
        List<GameAdmin> gameAdminList = gameAdminRepository.findAll();
        assertThat(gameAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameAdmin() throws Exception {
        // Initialize the database
        gameAdminService.save(gameAdmin);

        int databaseSizeBeforeDelete = gameAdminRepository.findAll().size();

        // Delete the gameAdmin
        restGameAdminMockMvc.perform(delete("/api/game-admins/{id}", gameAdmin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameAdmin> gameAdminList = gameAdminRepository.findAll();
        assertThat(gameAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
