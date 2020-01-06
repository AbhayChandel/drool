package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopicUserLike;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import com.hexlindia.drool.discussion.to.mapper.DiscussionTopicMapper;
import org.junit.jupiter.api.Assertions;
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
class DiscussionTopicImplTest {

    private DiscussionTopicImpl discussionTopicImplSpy;

    @Mock
    private DiscussionTopicMapper discussionTopicMapperMocked;

    @Mock
    private DiscussionTopicRepository discussionTopicRepositoryMocked;

    @Mock
    private DiscussionTopicUserLike discussionTopicUserLikeMocked;

    @BeforeEach
    void setUp() {
        this.discussionTopicImplSpy = Mockito.spy(new DiscussionTopicImpl(this.discussionTopicRepositoryMocked, this.discussionTopicMapperMocked, discussionTopicUserLikeMocked));
    }

    @Test
    void post_PassingObjectToRepositoryLayer() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity("This is a dummy topic", 7L, false);
        when(this.discussionTopicMapperMocked.toEntity(any())).thenReturn(discussionTopicEntityMocked);
        when(this.discussionTopicRepositoryMocked.save(any())).thenReturn(discussionTopicEntityMocked);
        this.discussionTopicImplSpy.post(null);
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals("This is a dummy topic", discussionTopicEntityArgumentCaptor.getValue().getTopic());
        assertEquals(7L, discussionTopicEntityArgumentCaptor.getValue().getUserId());
    }

    @Test
    void post_ObjectReturnedFromRepositoryLayerIsReceived() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity("This is a dummy topic", 7L, false);
        discussionTopicEntityMocked.setId(100L);
        when(this.discussionTopicMapperMocked.toEntity(any())).thenReturn(discussionTopicEntityMocked);
        when(this.discussionTopicRepositoryMocked.save(any())).thenReturn(discussionTopicEntityMocked);
        DiscussionTopicTo discussionTopicToMocked = new DiscussionTopicTo();
        discussionTopicToMocked.setId(discussionTopicEntityMocked.getId());
        discussionTopicToMocked.setUserId(discussionTopicEntityMocked.getUserId());
        when(this.discussionTopicMapperMocked.toTransferObject(discussionTopicEntityMocked)).thenReturn(discussionTopicToMocked);
        DiscussionTopicTo discussionTopicTo = this.discussionTopicImplSpy.post(null);
        assertEquals(100L, discussionTopicTo.getId());
        assertEquals(7L, discussionTopicTo.getUserId());
    }

    @Test
    void findById_testPassingEntityToRepository() {
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(new DiscussionTopicEntity()));
        discussionTopicImplSpy.findById(100L);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(discussionTopicRepositoryMocked, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(100L, idArgumentCaptor.getValue());
    }

    @Test
    void findById_testFindUnavailableDiscussionTopic() {
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.empty());
        Assertions.assertThrows(DiscussionTopicNotFoundException.class, () -> discussionTopicImplSpy.findById(100L));
    }

    @Test
    void update_PassingObjectToRepositoryLayer() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity("This is a dummy topic", 7L, false);
        discussionTopicEntityMocked.setId(2L);
        when(this.discussionTopicMapperMocked.toEntity(any())).thenReturn(discussionTopicEntityMocked);
        Optional<DiscussionTopicEntity> discussionTopicEntityMockedOptional = Optional.of(discussionTopicEntityMocked);
        when(this.discussionTopicRepositoryMocked.findById(any())).thenReturn(discussionTopicEntityMockedOptional);
        this.discussionTopicImplSpy.updateTopicTitle(new DiscussionTopicTo(0L, "Dummy topic", 13L));
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(2L, discussionTopicEntityArgumentCaptor.getValue().getId());
        assertEquals("Dummy topic", discussionTopicEntityArgumentCaptor.getValue().getTopic());
        assertEquals(7L, discussionTopicEntityArgumentCaptor.getValue().getUserId());
        assertEquals(false, discussionTopicEntityArgumentCaptor.getValue().isActive());
    }

    @Test
    void incrementViewsByOne() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setViews(50);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.incrementViewsByOne(100L);
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(51, discussionTopicEntityArgumentCaptor.getValue().getViews());
    }

    @Test
    void incrementLikesByOne_saveToTopicEntity() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setLikes(12);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.incrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(13, discussionTopicEntityArgumentCaptor.getValue().getLikes());
    }

    @Test
    void incrementLikesByOne_saveToUserLikeEntity() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setLikes(12);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.incrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionTopicUserLikeId> userLikeIdArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicUserLikeId.class);
        verify(this.discussionTopicUserLikeMocked, times(1)).save(userLikeIdArgumentCaptor.capture());
        assertEquals(5L, userLikeIdArgumentCaptor.getValue().getUserId());
        assertEquals(100L, userLikeIdArgumentCaptor.getValue().getTopicId());
    }

    @Test
    void decrementLikesByOne_saveToTopicEntity() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setLikes(12);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.decrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(11, discussionTopicEntityArgumentCaptor.getValue().getLikes());
    }

    @Test
    void decrementLikesByOne_saveNewUserLikeEntity() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setLikes(12);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.decrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionTopicUserLikeId> userLikeIdArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicUserLikeId.class);
        verify(this.discussionTopicUserLikeMocked, times(1)).remove(userLikeIdArgumentCaptor.capture());
        assertEquals(5L, userLikeIdArgumentCaptor.getValue().getUserId());
        assertEquals(100L, userLikeIdArgumentCaptor.getValue().getTopicId());
    }

    @Test
    void incrementRepliesByOne() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setReplies(5);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.incrementRepliesByOne(100L);
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(6, discussionTopicEntityArgumentCaptor.getValue().getReplies());
    }

    @Test
    void decrementRepliesByOne() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setReplies(5);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.decrementRepliesByOne(100L);
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
        assertEquals(4, discussionTopicEntityArgumentCaptor.getValue().getReplies());
    }

    @Test
    void setDateLastActiveToNow() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setReplies(5);
        when(this.discussionTopicRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionTopicEntity));
        discussionTopicImplSpy.setLastDateActiveToNow(100L);
        ArgumentCaptor<DiscussionTopicEntity> discussionTopicEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicEntity.class);
        verify(this.discussionTopicRepositoryMocked, times(1)).save(discussionTopicEntityArgumentCaptor.capture());
    }

}