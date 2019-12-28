package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.DiscussionTopicUserLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionTopicUserLikeImplTest {

    private DiscussionTopicUserLikeImpl discussionTopicUserLikeImplSpy;

    @Mock
    private DiscussionTopicUserLikeRepository discussionTopicUserLikeRepositoryMocked;

    @BeforeEach
    void setUp() {
        this.discussionTopicUserLikeImplSpy = Mockito.spy(new DiscussionTopicUserLikeImpl(this.discussionTopicUserLikeRepositoryMocked));
    }

    @Test
    void save_passingObjectToRepositoryLayer() {
        DiscussionTopicUserLikeId discussionTopicUserLikeIdMocked = new DiscussionTopicUserLikeId(29L, 11L);
        this.discussionTopicUserLikeImplSpy.save(discussionTopicUserLikeIdMocked);
        ArgumentCaptor<DiscussionTopicUserLikeEntity> discussionTopicUserLikeEntityArgumentCaptorEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicUserLikeEntity.class);
        verify(this.discussionTopicUserLikeRepositoryMocked, times(1)).save(discussionTopicUserLikeEntityArgumentCaptorEntityArgumentCaptor.capture());
        assertEquals(29L, discussionTopicUserLikeEntityArgumentCaptorEntityArgumentCaptor.getValue().getId().getUserId());
        assertEquals(11L, discussionTopicUserLikeEntityArgumentCaptorEntityArgumentCaptor.getValue().getId().getTopicId());
    }

    @Test
    void remove_passingObjectToRepositoryLayer() {
        DiscussionTopicUserLikeId discussionTopicUserLikeIdMocked = new DiscussionTopicUserLikeId(29L, 11L);
        DiscussionTopicUserLikeEntity discussionTopicUserLikeEntityMocked = new DiscussionTopicUserLikeEntity(discussionTopicUserLikeIdMocked);
        when(this.discussionTopicUserLikeRepositoryMocked.findById(any())).thenReturn(Optional.of(discussionTopicUserLikeEntityMocked));
        this.discussionTopicUserLikeImplSpy.remove(discussionTopicUserLikeIdMocked);
        ArgumentCaptor<DiscussionTopicUserLikeId> discussionTopicUserLikeIdArgumentCaptorEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicUserLikeId.class);
        verify(this.discussionTopicUserLikeRepositoryMocked, times(1)).deleteById(discussionTopicUserLikeIdArgumentCaptorEntityArgumentCaptor.capture());
        assertEquals(29L, discussionTopicUserLikeIdArgumentCaptorEntityArgumentCaptor.getValue().getUserId());
        assertEquals(11L, discussionTopicUserLikeIdArgumentCaptorEntityArgumentCaptor.getValue().getTopicId());
    }
}