package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameManagerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameManager.class);
        GameManager gameManager1 = new GameManager();
        gameManager1.setId(1L);
        GameManager gameManager2 = new GameManager();
        gameManager2.setId(gameManager1.getId());
        assertThat(gameManager1).isEqualTo(gameManager2);
        gameManager2.setId(2L);
        assertThat(gameManager1).isNotEqualTo(gameManager2);
        gameManager1.setId(null);
        assertThat(gameManager1).isNotEqualTo(gameManager2);
    }
}
