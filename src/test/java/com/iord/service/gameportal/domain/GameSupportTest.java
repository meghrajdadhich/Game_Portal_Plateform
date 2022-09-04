package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameSupportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameSupport.class);
        GameSupport gameSupport1 = new GameSupport();
        gameSupport1.setId(1L);
        GameSupport gameSupport2 = new GameSupport();
        gameSupport2.setId(gameSupport1.getId());
        assertThat(gameSupport1).isEqualTo(gameSupport2);
        gameSupport2.setId(2L);
        assertThat(gameSupport1).isNotEqualTo(gameSupport2);
        gameSupport1.setId(null);
        assertThat(gameSupport1).isNotEqualTo(gameSupport2);
    }
}
