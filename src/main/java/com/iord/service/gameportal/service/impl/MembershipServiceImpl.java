package com.iord.service.gameportal.service.impl;

import com.iord.service.gameportal.service.MembershipService;
import com.iord.service.gameportal.domain.Membership;
import com.iord.service.gameportal.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Membership}.
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership save(Membership membership) {
        log.debug("Request to save Membership : {}", membership);
        return membershipRepository.save(membership);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Membership> findAll(Pageable pageable) {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Membership> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
    }
}
