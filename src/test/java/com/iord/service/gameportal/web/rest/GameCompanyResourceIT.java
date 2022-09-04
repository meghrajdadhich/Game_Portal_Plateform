package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameCompany;
import com.iord.service.gameportal.repository.GameCompanyRepository;
import com.iord.service.gameportal.service.GameCompanyService;

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
 * Integration tests for the {@link GameCompanyResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private GameCompanyRepository gameCompanyRepository;

    @Autowired
    private GameCompanyService gameCompanyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameCompanyMockMvc;

    private GameCompany gameCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameCompany createEntity(EntityManager em) {
        GameCompany gameCompany = new GameCompany()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return gameCompany;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameCompany createUpdatedEntity(EntityManager em) {
        GameCompany gameCompany = new GameCompany()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        return gameCompany;
    }

    @BeforeEach
    public void initTest() {
        gameCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameCompany() throws Exception {
        int databaseSizeBeforeCreate = gameCompanyRepository.findAll().size();
        // Create the GameCompany
        restGameCompanyMockMvc.perform(post("/api/game-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameCompany)))
            .andExpect(status().isCreated());

        // Validate the GameCompany in the database
        List<GameCompany> gameCompanyList = gameCompanyRepository.findAll();
        assertThat(gameCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        GameCompany testGameCompany = gameCompanyList.get(gameCompanyList.size() - 1);
        assertThat(testGameCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGameCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createGameCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameCompanyRepository.findAll().size();

        // Create the GameCompany with an existing ID
        gameCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameCompanyMockMvc.perform(post("/api/game-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameCompany)))
            .andExpect(status().isBadRequest());

        // Validate the GameCompany in the database
        List<GameCompany> gameCompanyList = gameCompanyRepository.findAll();
        assertThat(gameCompanyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameCompanies() throws Exception {
        // Initialize the database
        gameCompanyRepository.saveAndFlush(gameCompany);

        // Get all the gameCompanyList
        restGameCompanyMockMvc.perform(get("/api/game-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getGameCompany() throws Exception {
        // Initialize the database
        gameCompanyRepository.saveAndFlush(gameCompany);

        // Get the gameCompany
        restGameCompanyMockMvc.perform(get("/api/game-companies/{id}", gameCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingGameCompany() throws Exception {
        // Get the gameCompany
        restGameCompanyMockMvc.perform(get("/api/game-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameCompany() throws Exception {
        // Initialize the database
        gameCompanyService.save(gameCompany);

        int databaseSizeBeforeUpdate = gameCompanyRepository.findAll().size();

        // Update the gameCompany
        GameCompany updatedGameCompany = gameCompanyRepository.findById(gameCompany.getId()).get();
        // Disconnect from session so that the updates on updatedGameCompany are not directly saved in db
        em.detach(updatedGameCompany);
        updatedGameCompany
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restGameCompanyMockMvc.perform(put("/api/game-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameCompany)))
            .andExpect(status().isOk());

        // Validate the GameCompany in the database
        List<GameCompany> gameCompanyList = gameCompanyRepository.findAll();
        assertThat(gameCompanyList).hasSize(databaseSizeBeforeUpdate);
        GameCompany testGameCompany = gameCompanyList.get(gameCompanyList.size() - 1);
        assertThat(testGameCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGameCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingGameCompany() throws Exception {
        int databaseSizeBeforeUpdate = gameCompanyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameCompanyMockMvc.perform(put("/api/game-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameCompany)))
            .andExpect(status().isBadRequest());

        // Validate the GameCompany in the database
        List<GameCompany> gameCompanyList = gameCompanyRepository.findAll();
        assertThat(gameCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameCompany() throws Exception {
        // Initialize the database
        gameCompanyService.save(gameCompany);

        int databaseSizeBeforeDelete = gameCompanyRepository.findAll().size();

        // Delete the gameCompany
        restGameCompanyMockMvc.perform(delete("/api/game-companies/{id}", gameCompany.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameCompany> gameCompanyList = gameCompanyRepository.findAll();
        assertThat(gameCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
