package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.MembershipHistoryService;
import com.iord.service.gameportal.domain.MembershipHistory;
import com.iord.service.gameportal.repository.MembershipHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MembershipHistory}.
 */
@Service
@Transactional
public class MembershipHistoryServiceImpl implements MembershipHistoryService {

    private final Logger log = LoggerFactory.getLogger(MembershipHistoryServiceImpl.class);

    private final MembershipHistoryRepository membershipHistoryRepository;

    public MembershipHistoryServiceImpl(MembershipHistoryRepository membershipHistoryRepository) {
        this.membershipHistoryRepository = membershipHistoryRepository;
    }

    @Override
    public MembershipHistory save(MembershipHistory membershipHistory) {
        log.debug("Request to save MembershipHistory : {}", membershipHistory);
        return membershipHistoryRepository.save(membershipHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipHistory> findAll(Pageable pageable) {
        log.debug("Request to get all MembershipHistories");
        return membershipHistoryRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipHistory> findOne(Long id) {
        log.debug("Request to get MembershipHistory : {}", id);
        return membershipHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MembershipHistory : {}", id);
        membershipHistoryRepository.deleteById(id);
    }
}
