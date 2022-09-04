package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GamePointsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamePoints.class);
        GamePoints gamePoints1 = new GamePoints();
        gamePoints1.setId(1L);
        GamePoints gamePoints2 = new GamePoints();
        gamePoints2.setId(gamePoints1.getId());
        assertThat(gamePoints1).isEqualTo(gamePoints2);
        gamePoints2.setId(2L);
        assertThat(gamePoints1).isNotEqualTo(gamePoints2);
        gamePoints1.setId(null);
        assertThat(gamePoints1).isNotEqualTo(gamePoints2);
    }
}
