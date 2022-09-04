package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameHitRatioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameHitRatio.class);
        GameHitRatio gameHitRatio1 = new GameHitRatio();
        gameHitRatio1.setId(1L);
        GameHitRatio gameHitRatio2 = new GameHitRatio();
        gameHitRatio2.setId(gameHitRatio1.getId());
        assertThat(gameHitRatio1).isEqualTo(gameHitRatio2);
        gameHitRatio2.setId(2L);
        assertThat(gameHitRatio1).isNotEqualTo(gameHitRatio2);
        gameHitRatio1.setId(null);
        assertThat(gameHitRatio1).isNotEqualTo(gameHitRatio2);
    }
}
