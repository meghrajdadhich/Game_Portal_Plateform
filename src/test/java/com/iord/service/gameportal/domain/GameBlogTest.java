package com.iord.service.gameportal.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.iord.service.gameportal.web.rest.TestUtil;

public class GameBlogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameBlog.class);
        GameBlog gameBlog1 = new GameBlog();
        gameBlog1.setId(1L);
        GameBlog gameBlog2 = new GameBlog();
        gameBlog2.setId(gameBlog1.getId());
        assertThat(gameBlog1).isEqualTo(gameBlog2);
        gameBlog2.setId(2L);
        assertThat(gameBlog1).isNotEqualTo(gameBlog2);
        gameBlog1.setId(null);
        assertThat(gameBlog1).isNotEqualTo(gameBlog2);
    }
}
