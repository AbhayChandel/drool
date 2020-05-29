package com.hexlindia.drool.post.data.repository.impl;

import com.hexlindia.drool.post.data.repository.api.PostPageViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PostPageViewRepositoryImpl.class)
class PostPageViewRepositoryImplTest {

    @Autowired
    PostPageViewRepository postPageViewRepository;

    @Test
    void getPost() {
        PostPageView postPageView = postPageViewRepository.getPost(102L);
        assertNotNull(postPageView);
        assertEquals("102", postPageView.getId());
        assertEquals("article", postPageView.getType());
        assertEquals("How to choose the right shade", postPageView.getTitle());
        assertNotNull(postPageView.getDatePosted());
        assertEquals("3.4k", postPageView.getLikes());
        assertEquals("456.7k", postPageView.getViews());
        assertEquals("3", postPageView.getUsercardView().getId());
        assertEquals("sonam31", postPageView.getUsercardView().getUsername());
        assertNull(postPageView.getSourceVideoId());
        assertEquals("This is an article about picking the right lip color shade", postPageView.getText());
        assertEquals("xsztiz.jpg", postPageView.getCoverPicture());
    }
}