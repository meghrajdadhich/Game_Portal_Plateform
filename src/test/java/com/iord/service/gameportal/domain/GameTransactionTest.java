package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameTransaction.class);
        GameTransaction gameTransaction1 = new GameTransaction();
        gameTransaction1.setId(1L);
        GameTransaction gameTransaction2 = new GameTransaction();
        gameTransaction2.setId(gameTransaction1.getId());
        assertThat(gameTransaction1).isEqualTo(gameTransaction2);
        gameTransaction2.setId(2L);
        assertThat(gameTransaction1).isNotEqualTo(gameTransaction2);
        gameTransaction1.setId(null);
        assertThat(gameTransaction1).isNotEqualTo(gameTransaction2);
    }
}
