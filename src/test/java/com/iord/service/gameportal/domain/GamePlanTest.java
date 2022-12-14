package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GamePlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamePlan.class);
        GamePlan gamePlan1 = new GamePlan();
        gamePlan1.setId(1L);
        GamePlan gamePlan2 = new GamePlan();
        gamePlan2.setId(gamePlan1.getId());
        assertThat(gamePlan1).isEqualTo(gamePlan2);
        gamePlan2.setId(2L);
        assertThat(gamePlan1).isNotEqualTo(gamePlan2);
        gamePlan1.setId(null);
        assertThat(gamePlan1).isNotEqualTo(gamePlan2);
    }
}
