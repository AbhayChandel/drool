package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyMongoRepository;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionReplyDtoDocMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionReplyImplTest {

    private DiscussionReplyImpl discussionReplyImplSpy;

    @Mock
    private DiscussionReplyDtoDocMapper discussionReplyDtoDocMapperMocked;

    @Mock
    private DiscussionReplyMongoRepository discussionReplyMongoRepositoryMocked;

    @BeforeEach
    void setUp() {
        this.discussionReplyImplSpy = Mockito.spy(new DiscussionReplyImpl(discussionReplyMongoRepositoryMocked, discussionReplyDtoDocMapperMocked));
    }

    @Test
    void post_PassingObjectToRepositoryLayer() {
        DiscussionReplyDoc discussionReplyDocMocked = new DiscussionReplyDoc();
        discussionReplyDocMocked.setReply("This is a test reply");
        LocalDateTime datePosted = LocalDateTime.now();
        discussionReplyDocMocked.setDatePosted(datePosted);
        discussionReplyDocMocked.setActive(true);
        discussionReplyDocMocked.setLikes(50);
        ObjectId discussionId = new ObjectId();
        ObjectId userId = new ObjectId();
        discussionReplyDocMocked.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        ObjectId discussionIdMocked = new ObjectId();
        when(this.discussionReplyDtoDocMapperMocked.toDoc(any())).thenReturn(discussionReplyDocMocked);
        when(this.discussionReplyMongoRepositoryMocked.saveReply(discussionReplyDocMocked, discussionIdMocked)).thenReturn(true);
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        discussionReplyDto.setDiscussionId(discussionId.toHexString());
        this.discussionReplyImplSpy.saveReply(discussionReplyDto);
        ArgumentCaptor<DiscussionReplyDoc> discussionReplyDocArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyDoc.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyMongoRepositoryMocked, times(1)).saveReply(discussionReplyDocArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is a test reply", discussionReplyDocArgumentCaptor.getValue().getReply());
        assertTrue(discussionReplyDocArgumentCaptor.getValue().isActive());
        assertEquals(50, discussionReplyDocArgumentCaptor.getValue().getLikes());
        assertEquals(userId.toHexString(), discussionReplyDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", discussionReplyDocArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals(discussionId, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void updateReply_PassingObjectToRepositoryLayer() {
        String reply = "This is updated reply";
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        when(this.discussionReplyMongoRepositoryMocked.updateReply(reply, replyIdMocked, discussionIdMocked)).thenReturn(true);
        this.discussionReplyImplSpy.updateReply(reply, replyIdMocked.toHexString(), discussionIdMocked.toHexString());
        ArgumentCaptor<String> replyArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyMongoRepositoryMocked, times(1)).updateReply(replyArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is updated reply", replyArgumentCaptor.getValue());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void incrementLikes_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        ObjectId userIdMocked = new ObjectId();
        when(this.discussionReplyMongoRepositoryMocked.incrementLikes(replyIdMocked, discussionIdMocked)).thenReturn(301);
        this.discussionReplyImplSpy.incrementLikes(replyIdMocked.toHexString(), discussionIdMocked.toHexString(), userIdMocked.toHexString());
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyMongoRepositoryMocked, times(1)).incrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void decrementLikes_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        ObjectId userIdMocked = new ObjectId();
        when(this.discussionReplyMongoRepositoryMocked.decrementLikes(replyIdMocked, discussionIdMocked)).thenReturn(299);
        this.discussionReplyImplSpy.decrementLikes(replyIdMocked.toHexString(), discussionIdMocked.toHexString(), userIdMocked.toHexString());
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyMongoRepositoryMocked, times(1)).decrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void setInactive_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        Boolean status = false;
        when(this.discussionReplyMongoRepositoryMocked.setStatus(status, replyIdMocked, discussionIdMocked)).thenReturn(true);
        this.discussionReplyImplSpy.setStatus(status, replyIdMocked.toHexString(), discussionIdMocked.toHexString());
        ArgumentCaptor<Boolean> statusArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyMongoRepositoryMocked, times(1)).setStatus(statusArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertFalse(statusArgumentCaptor.getValue());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }


}