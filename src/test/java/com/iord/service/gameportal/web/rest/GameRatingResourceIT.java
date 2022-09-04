package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameRating;
import com.iord.service.gameportal.repository.GameRatingRepository;
import com.iord.service.gameportal.service.GameRatingService;

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
 * Integration tests for the {@link GameRatingResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameRatingResourceIT {

    private static final Long DEFAULT_GAMEID = 1L;
    private static final Long UPDATED_GAMEID = 2L;

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final Long DEFAULT_TIMESTAMP = 1L;
    private static final Long UPDATED_TIMESTAMP = 2L;

    @Autowired
    private GameRatingRepository gameRatingRepository;

    @Autowired
    private GameRatingService gameRatingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameRatingMockMvc;

    private GameRating gameRating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameRating createEntity(EntityManager em) {
        GameRating gameRating = new GameRating()
            .gameid(DEFAULT_GAMEID)
            .rating(DEFAULT_RATING)
            .timestamp(DEFAULT_TIMESTAMP);
        return gameRating;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameRating createUpdatedEntity(EntityManager em) {
        GameRating gameRating = new GameRating()
            .gameid(UPDATED_GAMEID)
            .rating(UPDATED_RATING)
            .timestamp(UPDATED_TIMESTAMP);
        return gameRating;
    }

    @BeforeEach
    public void initTest() {
        gameRating = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameRating() throws Exception {
        int databaseSizeBeforeCreate = gameRatingRepository.findAll().size();
        // Create the GameRating
        restGameRatingMockMvc.perform(post("/api/game-ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRating)))
            .andExpect(status().isCreated());

        // Validate the GameRating in the database
        List<GameRating> gameRatingList = gameRatingRepository.findAll();
        assertThat(gameRatingList).hasSize(databaseSizeBeforeCreate + 1);
        GameRating testGameRating = gameRatingList.get(gameRatingList.size() - 1);
        assertThat(testGameRating.getGameid()).isEqualTo(DEFAULT_GAMEID);
        assertThat(testGameRating.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testGameRating.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createGameRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRatingRepository.findAll().size();

        // Create the GameRating with an existing ID
        gameRating.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameRatingMockMvc.perform(post("/api/game-ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRating)))
            .andExpect(status().isBadRequest());

        // Validate the GameRating in the database
        List<GameRating> gameRatingList = gameRatingRepository.findAll();
        assertThat(gameRatingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameRatings() throws Exception {
        // Initialize the database
        gameRatingRepository.saveAndFlush(gameRating);

        // Get all the gameRatingList
        restGameRatingMockMvc.perform(get("/api/game-ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameid").value(hasItem(DEFAULT_GAMEID.intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.intValue())));
    }
    
    @Test
    @Transactional
    public void getGameRating() throws Exception {
        // Initialize the database
        gameRatingRepository.saveAndFlush(gameRating);

        // Get the gameRating
        restGameRatingMockMvc.perform(get("/api/game-ratings/{id}", gameRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameRating.getId().intValue()))
            .andExpect(jsonPath("$.gameid").value(DEFAULT_GAMEID.intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGameRating() throws Exception {
        // Get the gameRating
        restGameRatingMockMvc.perform(get("/api/game-ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameRating() throws Exception {
        // Initialize the database
        gameRatingService.save(gameRating);

        int databaseSizeBeforeUpdate = gameRatingRepository.findAll().size();

        // Update the gameRating
        GameRating updatedGameRating = gameRatingRepository.findById(gameRating.getId()).get();
        // Disconnect from session so that the updates on updatedGameRating are not directly saved in db
        em.detach(updatedGameRating);
        updatedGameRating
            .gameid(UPDATED_GAMEID)
            .rating(UPDATED_RATING)
            .timestamp(UPDATED_TIMESTAMP);

        restGameRatingMockMvc.perform(put("/api/game-ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameRating)))
            .andExpect(status().isOk());

        // Validate the GameRating in the database
        List<GameRating> gameRatingList = gameRatingRepository.findAll();
        assertThat(gameRatingList).hasSize(databaseSizeBeforeUpdate);
        GameRating testGameRating = gameRatingList.get(gameRatingList.size() - 1);
        assertThat(testGameRating.getGameid()).isEqualTo(UPDATED_GAMEID);
        assertThat(testGameRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testGameRating.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingGameRating() throws Exception {
        int databaseSizeBeforeUpdate = gameRatingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameRatingMockMvc.perform(put("/api/game-ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameRating)))
            .andExpect(status().isBadRequest());

        // Validate the GameRating in the database
        List<GameRating> gameRatingList = gameRatingRepository.findAll();
        assertThat(gameRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameRating() throws Exception {
        // Initialize the database
        gameRatingService.save(gameRating);

        int databaseSizeBeforeDelete = gameRatingRepository.findAll().size();

        // Delete the gameRating
        restGameRatingMockMvc.perform(delete("/api/game-ratings/{id}", gameRating.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameRating> gameRatingList = gameRatingRepository.findAll();
        assertThat(gameRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
