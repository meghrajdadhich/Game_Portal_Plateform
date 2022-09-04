package com.iord.service.gameportal.web.rest;

import com.iord.service.gameportal.GamePortalApp;
import com.iord.service.gameportal.domain.MembershipHistory;
import com.iord.service.gameportal.repository.MembershipHistoryRepository;
import com.iord.service.gameportal.service.MembershipHistoryService;

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
 * Integration tests for the {@link MembershipHistoryResource} REST controller.
 */
@SpringBootTest(classes = GamePortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembershipHistoryResourceIT {

    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final Long UPDATED_MEMBER_ID = 2L;

    private static final Long DEFAULT_PAYMENT_ID = 1L;
    private static final Long UPDATED_PAYMENT_ID = 2L;

    private static final Long DEFAULT_PAYMENT_DATE = 1L;
    private static final Long UPDATED_PAYMENT_DATE = 2L;

    private static final Long DEFAULT_EXPERE_DATE = 1L;
    private static final Long UPDATED_EXPERE_DATE = 2L;

    private static final String DEFAULT_MEMBER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_STATUS = "BBBBBBBBBB";

    @Autowired
    private MembershipHistoryRepository membershipHistoryRepository;

    @Autowired
    private MembershipHistoryService membershipHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipHistoryMockMvc;

    private MembershipHistory membershipHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipHistory createEntity(EntityManager em) {
        MembershipHistory membershipHistory = new MembershipHistory()
            .memberId(DEFAULT_MEMBER_ID)
            .paymentId(DEFAULT_PAYMENT_ID)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .expereDate(DEFAULT_EXPERE_DATE)
            .memberStatus(DEFAULT_MEMBER_STATUS);
        return membershipHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipHistory createUpdatedEntity(EntityManager em) {
        MembershipHistory membershipHistory = new MembershipHistory()
            .memberId(UPDATED_MEMBER_ID)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .expereDate(UPDATED_EXPERE_DATE)
            .memberStatus(UPDATED_MEMBER_STATUS);
        return membershipHistory;
    }

    @BeforeEach
    public void initTest() {
        membershipHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembershipHistory() throws Exception {
        int databaseSizeBeforeCreate = membershipHistoryRepository.findAll().size();
        // Create the MembershipHistory
        restMembershipHistoryMockMvc.perform(post("/api/membership-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipHistory)))
            .andExpect(status().isCreated());

        // Validate the MembershipHistory in the database
        List<MembershipHistory> membershipHistoryList = membershipHistoryRepository.findAll();
        assertThat(membershipHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MembershipHistory testMembershipHistory = membershipHistoryList.get(membershipHistoryList.size() - 1);
        assertThat(testMembershipHistory.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testMembershipHistory.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testMembershipHistory.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testMembershipHistory.getExpereDate()).isEqualTo(DEFAULT_EXPERE_DATE);
        assertThat(testMembershipHistory.getMemberStatus()).isEqualTo(DEFAULT_MEMBER_STATUS);
    }

    @Test
    @Transactional
    public void createMembershipHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipHistoryRepository.findAll().size();

        // Create the MembershipHistory with an existing ID
        membershipHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipHistoryMockMvc.perform(post("/api/membership-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipHistory)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipHistory in the database
        List<MembershipHistory> membershipHistoryList = membershipHistoryRepository.findAll();
        assertThat(membershipHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMembershipHistories() throws Exception {
        // Initialize the database
        membershipHistoryRepository.saveAndFlush(membershipHistory);

        // Get all the membershipHistoryList
        restMembershipHistoryMockMvc.perform(get("/api/membership-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membershipHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID.intValue())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.intValue())))
            .andExpect(jsonPath("$.[*].expereDate").value(hasItem(DEFAULT_EXPERE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].memberStatus").value(hasItem(DEFAULT_MEMBER_STATUS)));
    }
    
    @Test
    @Transactional
    public void getMembershipHistory() throws Exception {
        // Initialize the database
        membershipHistoryRepository.saveAndFlush(membershipHistory);

        // Get the membershipHistory
        restMembershipHistoryMockMvc.perform(get("/api/membership-histories/{id}", membershipHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membershipHistory.getId().intValue()))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID.intValue()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.intValue()))
            .andExpect(jsonPath("$.expereDate").value(DEFAULT_EXPERE_DATE.intValue()))
            .andExpect(jsonPath("$.memberStatus").value(DEFAULT_MEMBER_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingMembershipHistory() throws Exception {
        // Get the membershipHistory
        restMembershipHistoryMockMvc.perform(get("/api/membership-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembershipHistory() throws Exception {
        // Initialize the database
        membershipHistoryService.save(membershipHistory);

        int databaseSizeBeforeUpdate = membershipHistoryRepository.findAll().size();

        // Update the membershipHistory
        MembershipHistory updatedMembershipHistory = membershipHistoryRepository.findById(membershipHistory.getId()).get();
        // Disconnect from session so that the updates on updatedMembershipHistory are not directly saved in db
        em.detach(updatedMembershipHistory);
        updatedMembershipHistory
            .memberId(UPDATED_MEMBER_ID)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .expereDate(UPDATED_EXPERE_DATE)
            .memberStatus(UPDATED_MEMBER_STATUS);

        restMembershipHistoryMockMvc.perform(put("/api/membership-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembershipHistory)))
            .andExpect(status().isOk());

        // Validate the MembershipHistory in the database
        List<MembershipHistory> membershipHistoryList = membershipHistoryRepository.findAll();
        assertThat(membershipHistoryList).hasSize(databaseSizeBeforeUpdate);
        MembershipHistory testMembershipHistory = membershipHistoryList.get(membershipHistoryList.size() - 1);
        assertThat(testMembershipHistory.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testMembershipHistory.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testMembershipHistory.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testMembershipHistory.getExpereDate()).isEqualTo(UPDATED_EXPERE_DATE);
        assertThat(testMembershipHistory.getMemberStatus()).isEqualTo(UPDATED_MEMBER_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMembershipHistory() throws Exception {
        int databaseSizeBeforeUpdate = membershipHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipHistoryMockMvc.perform(put("/api/membership-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipHistory)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipHistory in the database
        List<MembershipHistory> membershipHistoryList = membershipHistoryRepository.findAll();
        assertThat(membershipHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembershipHistory() throws Exception {
        // Initialize the database
        membershipHistoryService.save(membershipHistory);

        int databaseSizeBeforeDelete = membershipHistoryRepository.findAll().size();

        // Delete the membershipHistory
        restMembershipHistoryMockMvc.perform(delete("/api/membership-histories/{id}", membershipHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembershipHistory> membershipHistoryList = membershipHistoryRepository.findAll();
        assertThat(membershipHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
