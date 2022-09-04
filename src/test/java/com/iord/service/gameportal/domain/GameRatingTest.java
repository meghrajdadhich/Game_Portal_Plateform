package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameRatingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameRating.class);
        GameRating gameRating1 = new GameRating();
        gameRating1.setId(1L);
        GameRating gameRating2 = new GameRating();
        gameRating2.setId(gameRating1.getId());
        assertThat(gameRating1).isEqualTo(gameRating2);
        gameRating2.setId(2L);
        assertThat(gameRating1).isNotEqualTo(gameRating2);
        gameRating1.setId(null);
        assertThat(gameRating1).isNotEqualTo(gameRating2);
    }
}
