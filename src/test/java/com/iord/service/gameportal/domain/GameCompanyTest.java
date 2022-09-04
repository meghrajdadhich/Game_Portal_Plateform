package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameCompanyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameCompany.class);
        GameCompany gameCompany1 = new GameCompany();
        gameCompany1.setId(1L);
        GameCompany gameCompany2 = new GameCompany();
        gameCompany2.setId(gameCompany1.getId());
        assertThat(gameCompany1).isEqualTo(gameCompany2);
        gameCompany2.setId(2L);
        assertThat(gameCompany1).isNotEqualTo(gameCompany2);
        gameCompany1.setId(null);
        assertThat(gameCompany1).isNotEqualTo(gameCompany2);
    }
}
