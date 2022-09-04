package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.Comments;
import com.iord.service.gameportal.repository.CommentsRepository;
import com.iord.service.gameportal.service.CommentsService;

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
 * Integration tests for the {@link CommentsResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentsResourceIT {

    private static final String DEFAULT_GAMENAME = "AAAAAAAAAA";
    private static final String UPDATED_GAMENAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentsMockMvc;

    private Comments comments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createEntity(EntityManager em) {
        Comments comments = new Comments()
            .gamename(DEFAULT_GAMENAME)
            .comment(DEFAULT_COMMENT);
        return comments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createUpdatedEntity(EntityManager em) {
        Comments comments = new Comments()
            .gamename(UPDATED_GAMENAME)
            .comment(UPDATED_COMMENT);
        return comments;
    }

    @BeforeEach
    public void initTest() {
        comments = createEntity(em);
    }

    @Test
    @Transactional
    public void createComments() throws Exception {
        int databaseSizeBeforeCreate = commentsRepository.findAll().size();
        // Create the Comments
        restCommentsMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comments)))
            .andExpect(status().isCreated());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeCreate + 1);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getGamename()).isEqualTo(DEFAULT_GAMENAME);
        assertThat(testComments.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentsRepository.findAll().size();

        // Create the Comments with an existing ID
        comments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentsMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comments)))
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get all the commentsList
        restCommentsMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comments.getId().intValue())))
            .andExpect(jsonPath("$.[*].gamename").value(hasItem(DEFAULT_GAMENAME)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get the comments
        restCommentsMockMvc.perform(get("/api/comments/{id}", comments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comments.getId().intValue()))
            .andExpect(jsonPath("$.gamename").value(DEFAULT_GAMENAME))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }
    @Test
    @Transactional
    public void getNonExistingComments() throws Exception {
        // Get the comments
        restCommentsMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComments() throws Exception {
        // Initialize the database
        commentsService.save(comments);

        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // Update the comments
        Comments updatedComments = commentsRepository.findById(comments.getId()).get();
        // Disconnect from session so that the updates on updatedComments are not directly saved in db
        em.detach(updatedComments);
        updatedComments
            .gamename(UPDATED_GAMENAME)
            .comment(UPDATED_COMMENT);

        restCommentsMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComments)))
            .andExpect(status().isOk());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getGamename()).isEqualTo(UPDATED_GAMENAME);
        assertThat(testComments.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comments)))
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComments() throws Exception {
        // Initialize the database
        commentsService.save(comments);

        int databaseSizeBeforeDelete = commentsRepository.findAll().size();

        // Delete the comments
        restCommentsMockMvc.perform(delete("/api/comments/{id}", comments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
