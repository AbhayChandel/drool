package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReplyUserLike;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.data.repository.DiscussionReplyRepository;
import com.hexlindia.drool.discussion.exception.DiscussionReplyNotFoundException;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import com.hexlindia.drool.discussion.to.mapper.DiscussionReplyMapper;
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
class DiscussionReplyImplTest {

    private DiscussionReplyImpl discussionReplyImplSpy;

    @Mock
    private DiscussionReplyMapper discussionReplyMapperMocked;

    @Mock
    private DiscussionReplyRepository discussionReplyRepositoryMocked;

    @Mock
    private DiscussionReplyUserLike discussionReplyUserLikeMocked;

    @Mock
    private DiscussionTopic discussionTopicMocked;

    @BeforeEach
    void setUp() {
        this.discussionReplyImplSpy = Mockito.spy(new DiscussionReplyImpl(this.discussionReplyRepositoryMocked, this.discussionReplyMapperMocked, discussionReplyUserLikeMocked, discussionTopicMocked));
    }

    @Test
    void post_PassingObjectToRepositoryLayer() {
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("This is a dummy reply", 7L);
        when(this.discussionReplyMapperMocked.toEntity(any())).thenReturn(discussionReplyEntityMocked);
        when(this.discussionReplyRepositoryMocked.save(any())).thenReturn(discussionReplyEntityMocked);
        DiscussionReplyTo discussionReplyToMocked = new DiscussionReplyTo();
        discussionReplyToMocked.setId(1L);
        this.discussionReplyImplSpy.post(discussionReplyToMocked);
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).save(discussionReplyEntityArgumentCaptor.capture());
        assertEquals("This is a dummy reply", discussionReplyEntityArgumentCaptor.getValue().getReply());
        assertEquals(7L, discussionReplyEntityArgumentCaptor.getValue().getUserId());
    }

    @Test
    void post_discussionTopicIncrementReplyCountCalled() {
        DiscussionReplyTo discussionReplyToMocked = new DiscussionReplyTo();
        discussionReplyToMocked.setDiscussionTopicId(1L);
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("This is another dummy reply", 7L);
        when(this.discussionReplyMapperMocked.toEntity(any())).thenReturn(discussionReplyEntityMocked);
        when(this.discussionReplyRepositoryMocked.save(any())).thenReturn(discussionReplyEntityMocked);
        this.discussionReplyImplSpy.post(discussionReplyToMocked);
        ArgumentCaptor<Long> discussionTopicIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionTopicMocked, times(1)).saveReply(discussionReplyEntityArgumentCaptor.capture(), discussionTopicIdArgumentCaptor.capture());
        assertEquals("This is another dummy reply", discussionReplyEntityArgumentCaptor.getValue().getReply());
        assertEquals(7L, discussionReplyEntityArgumentCaptor.getValue().getUserId());
        assertEquals(1L, discussionTopicIdArgumentCaptor.getValue().longValue());
    }

    @Test
    void findById_testPassingEntityToRepository() {
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.of(new DiscussionReplyEntity()));
        discussionReplyImplSpy.findById(100L);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(discussionReplyRepositoryMocked, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(100L, idArgumentCaptor.getValue());
    }

    @Test
    void findById_testFindNonExistingReply() {
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.empty());
        Assertions.assertThrows(DiscussionReplyNotFoundException.class, () -> discussionReplyImplSpy.findById(100L));
    }

    @Test
    void updateReply_PassingObjectToRepositoryLayer() {
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("It is a test reply", 7L);
        discussionReplyEntityMocked.setId(2L);
        when(this.discussionReplyMapperMocked.toEntity(any())).thenReturn(discussionReplyEntityMocked);
        Optional<DiscussionReplyEntity> discussionReplyEntityMockedOptional = Optional.of(discussionReplyEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(any())).thenReturn(discussionReplyEntityMockedOptional);
        this.discussionReplyImplSpy.updateReply(new DiscussionReplyTo(0L, 5L, "Dummy topic", 13L));
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).save(discussionReplyEntityArgumentCaptor.capture());
        assertEquals(2L, discussionReplyEntityArgumentCaptor.getValue().getId());
        assertEquals("Dummy topic", discussionReplyEntityArgumentCaptor.getValue().getReply());
        assertEquals(7L, discussionReplyEntityArgumentCaptor.getValue().getUserId());
    }

    @Test
    void incrementLikesByOne_saveToReplyEntity() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(1L);
        DiscussionReplyEntity discussionReplyEntity = new DiscussionReplyEntity();
        discussionReplyEntity.setLikes(22);
        discussionReplyEntity.setDiscussionTopicEntity(discussionTopicEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionReplyEntity));
        discussionReplyImplSpy.incrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).save(discussionReplyEntityArgumentCaptor.capture());
        assertEquals(23, discussionReplyEntityArgumentCaptor.getValue().getLikes());
    }

    @Test
    void incrementLikesByOne_saveToReplyUserLikeEntity() {
        DiscussionReplyEntity discussionReplyEntity = new DiscussionReplyEntity();
        discussionReplyEntity.setLikes(12);
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(1L);
        discussionReplyEntity.setDiscussionTopicEntity(discussionTopicEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionReplyEntity));
        discussionReplyImplSpy.incrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionReplyUserLikeId> userLikeIdArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyUserLikeId.class);
        verify(this.discussionReplyUserLikeMocked, times(1)).save(userLikeIdArgumentCaptor.capture());
        assertEquals(5L, userLikeIdArgumentCaptor.getValue().getUserId());
        assertEquals(100L, userLikeIdArgumentCaptor.getValue().getReplyId());
    }

    @Test
    void decrementLikesByOne_saveToReplyEntity() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(1L);
        DiscussionReplyEntity discussionReplyEntity = new DiscussionReplyEntity();
        discussionReplyEntity.setLikes(42);
        discussionReplyEntity.setDiscussionTopicEntity(discussionTopicEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionReplyEntity));
        discussionReplyImplSpy.decrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).save(discussionReplyEntityArgumentCaptor.capture());
        assertEquals(41, discussionReplyEntityArgumentCaptor.getValue().getLikes());
    }

    @Test
    void decrementLikesByOne_savToUserLikeEntity() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(1L);
        DiscussionReplyEntity discussionReplyEntity = new DiscussionReplyEntity();
        discussionReplyEntity.setLikes(32);
        discussionReplyEntity.setDiscussionTopicEntity(discussionTopicEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(100L)).thenReturn(Optional.of(discussionReplyEntity));
        discussionReplyImplSpy.decrementLikesByOne(new ActivityTo(100L, 5L));
        ArgumentCaptor<DiscussionReplyUserLikeId> userLikeIdArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyUserLikeId.class);
        verify(this.discussionReplyUserLikeMocked, times(1)).remove(userLikeIdArgumentCaptor.capture());
        assertEquals(5L, userLikeIdArgumentCaptor.getValue().getUserId());
        assertEquals(100L, userLikeIdArgumentCaptor.getValue().getReplyId());
    }

    @Test
    void deactivateReply_PassingObjectToRepositoryLayer() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(1L);
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("This reply will be deactivated", 7L);
        discussionReplyEntityMocked.setId(2L);
        discussionReplyEntityMocked.setActive(true);
        discussionReplyEntityMocked.setDiscussionTopicEntity(discussionTopicEntityMocked);
        Optional<DiscussionReplyEntity> discussionReplyEntityMockedOptional = Optional.of(discussionReplyEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(any())).thenReturn(discussionReplyEntityMockedOptional);
        when(this.discussionReplyRepositoryMocked.save(any())).thenReturn(discussionReplyEntityMocked);
        this.discussionReplyImplSpy.deactivateReply(5L);
        ArgumentCaptor<DiscussionReplyEntity> discussionReplyEntityArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyEntity.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).save(discussionReplyEntityArgumentCaptor.capture());
        assertEquals(2L, discussionReplyEntityArgumentCaptor.getValue().getId());
        assertEquals("This reply will be deactivated", discussionReplyEntityArgumentCaptor.getValue().getReply());
        assertEquals(7L, discussionReplyEntityArgumentCaptor.getValue().getUserId());
        assertEquals(false, discussionReplyEntityArgumentCaptor.getValue().isActive());
    }

    @Test
    void deactivateReply_discussionTopicDecrementReplyCountCalled() {
        DiscussionTopicEntity discussionTopicEntityMocked = new DiscussionTopicEntity();
        discussionTopicEntityMocked.setId(33L);
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("This reply will be deactivated and the reply cound will be decremented", 7L);
        discussionReplyEntityMocked.setId(2L);
        discussionReplyEntityMocked.setActive(true);
        discussionReplyEntityMocked.setDiscussionTopicEntity(discussionTopicEntityMocked);
        Optional<DiscussionReplyEntity> discussionReplyEntityMockedOptional = Optional.of(discussionReplyEntityMocked);
        when(this.discussionReplyRepositoryMocked.findById(any())).thenReturn(discussionReplyEntityMockedOptional);
        when(this.discussionReplyRepositoryMocked.save(any())).thenReturn(discussionReplyEntityMocked);
        this.discussionReplyImplSpy.deactivateReply(5L);
        ArgumentCaptor<Long> discussionTopicIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionTopicMocked, times(1)).decrementRepliesByOne(discussionTopicIdArgumentCaptor.capture());
        assertEquals(33L, discussionTopicIdArgumentCaptor.getValue().intValue());
    }
}