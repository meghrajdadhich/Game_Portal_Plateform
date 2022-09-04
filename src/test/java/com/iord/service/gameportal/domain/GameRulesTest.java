package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameRulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameRules.class);
        GameRules gameRules1 = new GameRules();
        gameRules1.setId(1L);
        GameRules gameRules2 = new GameRules();
        gameRules2.setId(gameRules1.getId());
        assertThat(gameRules1).isEqualTo(gameRules2);
        gameRules2.setId(2L);
        assertThat(gameRules1).isNotEqualTo(gameRules2);
        gameRules1.setId(null);
        assertThat(gameRules1).isNotEqualTo(gameRules2);
    }
}
