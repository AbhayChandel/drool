package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;
import com.hexlindia.drool.discussion.data.repository.DiscussionReplyUserLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionReplyUserLikeImplTest {

    private DiscussionReplyUserLikeImpl discussionReplyUserLikeImplSpy;

    @Mock
    private DiscussionReplyUserLikeRepository discussionReplyUserLikeRepositoryMocked;

    @BeforeEach
    void setUp() {
        this.discussionReplyUserLikeImplSpy = Mockito.spy(new DiscussionReplyUserLikeImpl(this.discussionReplyUserLikeRepositoryMocked));
    }

    @Test
    void save_passingObjectToRepositoryLayer() {
        DiscussionReplyUserLikeId discussionReplyUserLikeIdMocked = new DiscussionReplyUserLikeId(9L, 11L);
        this.discussionReplyUserLikeImplSpy.save(discussionReplyUserLikeIdMocked);
        ArgumentCaptor<DiscussionReplyUserLikeEntity> discussionReplyUserLikeEntityArgumentCaptorEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyUserLikeEntity.class);
        verify(this.discussionReplyUserLikeRepositoryMocked, times(1)).save(discussionReplyUserLikeEntityArgumentCaptorEntityArgumentCaptor.capture());
        assertEquals(9L, discussionReplyUserLikeEntityArgumentCaptorEntityArgumentCaptor.getValue().getId().getUserId());
        assertEquals(11L, discussionReplyUserLikeEntityArgumentCaptorEntityArgumentCaptor.getValue().getId().getReplyId());
    }

    @Test
    void remove() {
        DiscussionReplyUserLikeId discussionReplyUserLikeIdMocked = new DiscussionReplyUserLikeId(9L, 15L);
        DiscussionReplyUserLikeEntity discussionReplyUserLikeEntityMocked = new DiscussionReplyUserLikeEntity(discussionReplyUserLikeIdMocked);
        when(this.discussionReplyUserLikeRepositoryMocked.findById(any())).thenReturn(Optional.of(discussionReplyUserLikeEntityMocked));
        this.discussionReplyUserLikeImplSpy.remove(discussionReplyUserLikeIdMocked);
        ArgumentCaptor<DiscussionReplyUserLikeId> discussionReplyUserLikeIdArgumentCaptorEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyUserLikeId.class);
        verify(this.discussionReplyUserLikeRepositoryMocked, times(1)).deleteById(discussionReplyUserLikeIdArgumentCaptorEntityArgumentCaptor.capture());
        assertEquals(9L, discussionReplyUserLikeIdArgumentCaptorEntityArgumentCaptor.getValue().getUserId());
        assertEquals(15L, discussionReplyUserLikeIdArgumentCaptorEntityArgumentCaptor.getValue().getReplyId());
    }
}