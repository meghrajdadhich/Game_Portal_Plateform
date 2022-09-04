package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class MembershipHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipHistory.class);
        MembershipHistory membershipHistory1 = new MembershipHistory();
        membershipHistory1.setId(1L);
        MembershipHistory membershipHistory2 = new MembershipHistory();
        membershipHistory2.setId(membershipHistory1.getId());
        assertThat(membershipHistory1).isEqualTo(membershipHistory2);
        membershipHistory2.setId(2L);
        assertThat(membershipHistory1).isNotEqualTo(membershipHistory2);
        membershipHistory1.setId(null);
        assertThat(membershipHistory1).isNotEqualTo(membershipHistory2);
    }
}
