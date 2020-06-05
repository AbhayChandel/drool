package com.hexlindia.drool.post.data.repository.impl;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.repository.api.PostViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PostViewRepositoryImpl.class)
class PostViewRepositoryImplTest {

    @Autowired
    PostViewRepository postViewRepository;

    @Test
    void getPost_post_found() {
        PostPageView postPageView = postViewRepository.getPost(102L, PostType2.ARTICLE).get();
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
        assertEquals("4", postPageView.getTotalComments());
    }

    @Test
    void getPost_post_not_found() {
        Optional<PostPageView> postPageView = postViewRepository.getPost(10002L, PostType2.ARTICLE);
        assertFalse(postPageView.isPresent());
    }
}