package com.hexlindia.drool.post.business.impl;

import com.hexlindia.drool.post.business.api.PostView;
import com.hexlindia.drool.post.business.exception.PostNotFoundException;
import com.hexlindia.drool.post.data.repository.impl.PostViewRepositoryImpl;
import com.hexlindia.drool.post.view.PostPageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(PostViewRepositoryImpl.class)
class PostViewImplTest {

    @MockBean
    PostViewRepositoryImpl postViewRepositoryMock;

    PostView postView;

    @BeforeEach
    void setUp() {
        postView = Mockito.spy(new PostViewImpl(postViewRepositoryMock));
    }

    @Test
    void getPost_post_found() {
        when(postViewRepositoryMock.getPost(anyLong())).thenReturn(Optional.of(new PostPageView()));
        assertNotNull(postView.getPost("1"));
    }

    @Test
    void getPost_post_not_found() {
        when(postViewRepositoryMock.getPost(anyLong())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> {
            postView.getPost("1");
        });
    }
}