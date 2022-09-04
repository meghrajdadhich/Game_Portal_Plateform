package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GamePrivacyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamePrivacy.class);
        GamePrivacy gamePrivacy1 = new GamePrivacy();
        gamePrivacy1.setId(1L);
        GamePrivacy gamePrivacy2 = new GamePrivacy();
        gamePrivacy2.setId(gamePrivacy1.getId());
        assertThat(gamePrivacy1).isEqualTo(gamePrivacy2);
        gamePrivacy2.setId(2L);
        assertThat(gamePrivacy1).isNotEqualTo(gamePrivacy2);
        gamePrivacy1.setId(null);
        assertThat(gamePrivacy1).isNotEqualTo(gamePrivacy2);
    }
}
