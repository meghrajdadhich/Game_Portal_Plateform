package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.GameBlog;
import com.iord.service.gameportal.repository.GameBlogRepository;
import com.iord.service.gameportal.service.GameBlogService;

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
 * Integration tests for the {@link GameBlogResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameBlogResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GameBlogRepository gameBlogRepository;

    @Autowired
    private GameBlogService gameBlogService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameBlogMockMvc;

    private GameBlog gameBlog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameBlog createEntity(EntityManager em) {
        GameBlog gameBlog = new GameBlog()
            .title(DEFAULT_TITLE)
            .category(DEFAULT_CATEGORY)
            .description(DEFAULT_DESCRIPTION);
        return gameBlog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameBlog createUpdatedEntity(EntityManager em) {
        GameBlog gameBlog = new GameBlog()
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION);
        return gameBlog;
    }

    @BeforeEach
    public void initTest() {
        gameBlog = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameBlog() throws Exception {
        int databaseSizeBeforeCreate = gameBlogRepository.findAll().size();
        // Create the GameBlog
        restGameBlogMockMvc.perform(post("/api/game-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameBlog)))
            .andExpect(status().isCreated());

        // Validate the GameBlog in the database
        List<GameBlog> gameBlogList = gameBlogRepository.findAll();
        assertThat(gameBlogList).hasSize(databaseSizeBeforeCreate + 1);
        GameBlog testGameBlog = gameBlogList.get(gameBlogList.size() - 1);
        assertThat(testGameBlog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGameBlog.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testGameBlog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGameBlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameBlogRepository.findAll().size();

        // Create the GameBlog with an existing ID
        gameBlog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameBlogMockMvc.perform(post("/api/game-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameBlog)))
            .andExpect(status().isBadRequest());

        // Validate the GameBlog in the database
        List<GameBlog> gameBlogList = gameBlogRepository.findAll();
        assertThat(gameBlogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameBlogs() throws Exception {
        // Initialize the database
        gameBlogRepository.saveAndFlush(gameBlog);

        // Get all the gameBlogList
        restGameBlogMockMvc.perform(get("/api/game-blogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getGameBlog() throws Exception {
        // Initialize the database
        gameBlogRepository.saveAndFlush(gameBlog);

        // Get the gameBlog
        restGameBlogMockMvc.perform(get("/api/game-blogs/{id}", gameBlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameBlog.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingGameBlog() throws Exception {
        // Get the gameBlog
        restGameBlogMockMvc.perform(get("/api/game-blogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameBlog() throws Exception {
        // Initialize the database
        gameBlogService.save(gameBlog);

        int databaseSizeBeforeUpdate = gameBlogRepository.findAll().size();

        // Update the gameBlog
        GameBlog updatedGameBlog = gameBlogRepository.findById(gameBlog.getId()).get();
        // Disconnect from session so that the updates on updatedGameBlog are not directly saved in db
        em.detach(updatedGameBlog);
        updatedGameBlog
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION);

        restGameBlogMockMvc.perform(put("/api/game-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameBlog)))
            .andExpect(status().isOk());

        // Validate the GameBlog in the database
        List<GameBlog> gameBlogList = gameBlogRepository.findAll();
        assertThat(gameBlogList).hasSize(databaseSizeBeforeUpdate);
        GameBlog testGameBlog = gameBlogList.get(gameBlogList.size() - 1);
        assertThat(testGameBlog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGameBlog.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testGameBlog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGameBlog() throws Exception {
        int databaseSizeBeforeUpdate = gameBlogRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameBlogMockMvc.perform(put("/api/game-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameBlog)))
            .andExpect(status().isBadRequest());

        // Validate the GameBlog in the database
        List<GameBlog> gameBlogList = gameBlogRepository.findAll();
        assertThat(gameBlogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameBlog() throws Exception {
        // Initialize the database
        gameBlogService.save(gameBlog);

        int databaseSizeBeforeDelete = gameBlogRepository.findAll().size();

        // Delete the gameBlog
        restGameBlogMockMvc.perform(delete("/api/game-blogs/{id}", gameBlog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameBlog> gameBlogList = gameBlogRepository.findAll();
        assertThat(gameBlogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
