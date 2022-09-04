package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GamePlatformTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamePlatform.class);
        GamePlatform gamePlatform1 = new GamePlatform();
        gamePlatform1.setId(1L);
        GamePlatform gamePlatform2 = new GamePlatform();
        gamePlatform2.setId(gamePlatform1.getId());
        assertThat(gamePlatform1).isEqualTo(gamePlatform2);
        gamePlatform2.setId(2L);
        assertThat(gamePlatform1).isNotEqualTo(gamePlatform2);
        gamePlatform1.setId(null);
        assertThat(gamePlatform1).isNotEqualTo(gamePlatform2);
    }
}
