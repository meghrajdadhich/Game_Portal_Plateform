package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameTransaction;
import com.iord.service.gameportal.repository.GameTransactionRepository;
import com.iord.service.gameportal.service.GameTransactionService;

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
 * Integration tests for the {@link GameTransactionResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameTransactionResourceIT {

    private static final Long DEFAULT_TRANSACTION_ID = 1L;
    private static final Long UPDATED_TRANSACTION_ID = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_TRANSACTION_TIME_STAMP = 1L;
    private static final Long UPDATED_TRANSACTION_TIME_STAMP = 2L;

    @Autowired
    private GameTransactionRepository gameTransactionRepository;

    @Autowired
    private GameTransactionService gameTransactionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameTransactionMockMvc;

    private GameTransaction gameTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameTransaction createEntity(EntityManager em) {
        GameTransaction gameTransaction = new GameTransaction()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .userId(DEFAULT_USER_ID)
            .transactionTimeStamp(DEFAULT_TRANSACTION_TIME_STAMP);
        return gameTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameTransaction createUpdatedEntity(EntityManager em) {
        GameTransaction gameTransaction = new GameTransaction()
            .transactionId(UPDATED_TRANSACTION_ID)
            .userId(UPDATED_USER_ID)
            .transactionTimeStamp(UPDATED_TRANSACTION_TIME_STAMP);
        return gameTransaction;
    }

    @BeforeEach
    public void initTest() {
        gameTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameTransaction() throws Exception {
        int databaseSizeBeforeCreate = gameTransactionRepository.findAll().size();
        // Create the GameTransaction
        restGameTransactionMockMvc.perform(post("/api/game-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTransaction)))
            .andExpect(status().isCreated());

        // Validate the GameTransaction in the database
        List<GameTransaction> gameTransactionList = gameTransactionRepository.findAll();
        assertThat(gameTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        GameTransaction testGameTransaction = gameTransactionList.get(gameTransactionList.size() - 1);
        assertThat(testGameTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testGameTransaction.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGameTransaction.getTransactionTimeStamp()).isEqualTo(DEFAULT_TRANSACTION_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createGameTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameTransactionRepository.findAll().size();

        // Create the GameTransaction with an existing ID
        gameTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameTransactionMockMvc.perform(post("/api/game-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the GameTransaction in the database
        List<GameTransaction> gameTransactionList = gameTransactionRepository.findAll();
        assertThat(gameTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameTransactions() throws Exception {
        // Initialize the database
        gameTransactionRepository.saveAndFlush(gameTransaction);

        // Get all the gameTransactionList
        restGameTransactionMockMvc.perform(get("/api/game-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionTimeStamp").value(hasItem(DEFAULT_TRANSACTION_TIME_STAMP.intValue())));
    }
    
    @Test
    @Transactional
    public void getGameTransaction() throws Exception {
        // Initialize the database
        gameTransactionRepository.saveAndFlush(gameTransaction);

        // Get the gameTransaction
        restGameTransactionMockMvc.perform(get("/api/game-transactions/{id}", gameTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.transactionTimeStamp").value(DEFAULT_TRANSACTION_TIME_STAMP.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGameTransaction() throws Exception {
        // Get the gameTransaction
        restGameTransactionMockMvc.perform(get("/api/game-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameTransaction() throws Exception {
        // Initialize the database
        gameTransactionService.save(gameTransaction);

        int databaseSizeBeforeUpdate = gameTransactionRepository.findAll().size();

        // Update the gameTransaction
        GameTransaction updatedGameTransaction = gameTransactionRepository.findById(gameTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedGameTransaction are not directly saved in db
        em.detach(updatedGameTransaction);
        updatedGameTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .userId(UPDATED_USER_ID)
            .transactionTimeStamp(UPDATED_TRANSACTION_TIME_STAMP);

        restGameTransactionMockMvc.perform(put("/api/game-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameTransaction)))
            .andExpect(status().isOk());

        // Validate the GameTransaction in the database
        List<GameTransaction> gameTransactionList = gameTransactionRepository.findAll();
        assertThat(gameTransactionList).hasSize(databaseSizeBeforeUpdate);
        GameTransaction testGameTransaction = gameTransactionList.get(gameTransactionList.size() - 1);
        assertThat(testGameTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testGameTransaction.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGameTransaction.getTransactionTimeStamp()).isEqualTo(UPDATED_TRANSACTION_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingGameTransaction() throws Exception {
        int databaseSizeBeforeUpdate = gameTransactionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameTransactionMockMvc.perform(put("/api/game-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the GameTransaction in the database
        List<GameTransaction> gameTransactionList = gameTransactionRepository.findAll();
        assertThat(gameTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameTransaction() throws Exception {
        // Initialize the database
        gameTransactionService.save(gameTransaction);

        int databaseSizeBeforeDelete = gameTransactionRepository.findAll().size();

        // Delete the gameTransaction
        restGameTransactionMockMvc.perform(delete("/api/game-transactions/{id}", gameTransaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameTransaction> gameTransactionList = gameTransactionRepository.findAll();
        assertThat(gameTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
