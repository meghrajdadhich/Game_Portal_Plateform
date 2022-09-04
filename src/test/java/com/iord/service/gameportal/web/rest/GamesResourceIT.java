package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.Games;
import com.iord.service.gameportal.repository.GamesRepository;
import com.iord.service.gameportal.service.GamesService;

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
 * Integration tests for the {@link GamesResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GamesResourceIT {

    private static final Long DEFAULT_GAME_ID = 1L;
    private static final Long UPDATED_GAME_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private GamesService gamesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamesMockMvc;

    private Games games;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Games createEntity(EntityManager em) {
        Games games = new Games()
            .gameId(DEFAULT_GAME_ID)
            .name(DEFAULT_NAME);
        return games;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Games createUpdatedEntity(EntityManager em) {
        Games games = new Games()
            .gameId(UPDATED_GAME_ID)
            .name(UPDATED_NAME);
        return games;
    }

    @BeforeEach
    public void initTest() {
        games = createEntity(em);
    }

    @Test
    @Transactional
    public void createGames() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();
        // Create the Games
        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isCreated());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate + 1);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testGames.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGamesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();

        // Create the Games with an existing ID
        games.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList
        restGamesMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(games.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameId").value(hasItem(DEFAULT_GAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", games.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(games.getId().intValue()))
            .andExpect(jsonPath("$.gameId").value(DEFAULT_GAME_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGames() throws Exception {
        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGames() throws Exception {
        // Initialize the database
        gamesService.save(games);

        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // Update the games
        Games updatedGames = gamesRepository.findById(games.getId()).get();
        // Disconnect from session so that the updates on updatedGames are not directly saved in db
        em.detach(updatedGames);
        updatedGames
            .gameId(UPDATED_GAME_ID)
            .name(UPDATED_NAME);

        restGamesMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGames)))
            .andExpect(status().isOk());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getGameId()).isEqualTo(UPDATED_GAME_ID);
        assertThat(testGames.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGames() throws Exception {
        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamesMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGames() throws Exception {
        // Initialize the database
        gamesService.save(games);

        int databaseSizeBeforeDelete = gamesRepository.findAll().size();

        // Delete the games
        restGamesMockMvc.perform(delete("/api/games/{id}", games.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
