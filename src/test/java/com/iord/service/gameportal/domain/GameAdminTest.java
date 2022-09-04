package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameAdminTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameAdmin.class);
        GameAdmin gameAdmin1 = new GameAdmin();
        gameAdmin1.setId(1L);
        GameAdmin gameAdmin2 = new GameAdmin();
        gameAdmin2.setId(gameAdmin1.getId());
        assertThat(gameAdmin1).isEqualTo(gameAdmin2);
        gameAdmin2.setId(2L);
        assertThat(gameAdmin1).isNotEqualTo(gameAdmin2);
        gameAdmin1.setId(null);
        assertThat(gameAdmin1).isNotEqualTo(gameAdmin2);
    }
}
