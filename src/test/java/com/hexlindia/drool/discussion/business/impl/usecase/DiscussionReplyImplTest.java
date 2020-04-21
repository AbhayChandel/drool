package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyRepository;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionReplyDtoDocMapper;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionReplyImplTest {

    private DiscussionReplyImpl discussionReplyImplSpy;

    @Mock
    private DiscussionReplyDtoDocMapper discussionReplyDtoDocMapperMocked;

    @Mock
    private DiscussionReplyRepository discussionReplyRepositoryMocked;

    @BeforeEach
    void setUp() {
        this.discussionReplyImplSpy = Mockito.spy(new DiscussionReplyImpl(discussionReplyRepositoryMocked, discussionReplyDtoDocMapperMocked));
    }

    @Test
    void saveOrUpdate_PassingObjectToSaveRepositoryLayer() {
        DiscussionReplyDoc discussionReplyDocMocked = new DiscussionReplyDoc();
        discussionReplyDocMocked.setReply("This is a test reply");
        LocalDateTime datePosted = LocalDateTime.now();
        discussionReplyDocMocked.setDatePosted(datePosted);
        discussionReplyDocMocked.setLikes(50);
        ObjectId discussionId = new ObjectId();
        ObjectId userId = new ObjectId();
        discussionReplyDocMocked.setUserRef(new UserRef(userId, "shabana"));
        ObjectId discussionIdMocked = new ObjectId();
        when(this.discussionReplyDtoDocMapperMocked.toDoc(any())).thenReturn(discussionReplyDocMocked);
        when(this.discussionReplyRepositoryMocked.saveReply(discussionReplyDocMocked, discussionIdMocked)).thenReturn(true);
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        discussionReplyDto.setDiscussionId(discussionId.toHexString());
        this.discussionReplyImplSpy.saveOrUpdate(discussionReplyDto);
        ArgumentCaptor<DiscussionReplyDoc> discussionReplyDocArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyDoc.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).saveReply(discussionReplyDocArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is a test reply", discussionReplyDocArgumentCaptor.getValue().getReply());
        assertEquals(50, discussionReplyDocArgumentCaptor.getValue().getLikes());
        assertEquals(userId, discussionReplyDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", discussionReplyDocArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals(discussionId, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void saveOrUpdate_PassingObjectToUpdateRepositoryLayer() {
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        String reply = "This is updated reply";
        discussionReplyDto.setReply(reply);
        ObjectId replyIdMocked = new ObjectId();
        discussionReplyDto.setId(replyIdMocked.toHexString());
        ObjectId discussionIdMocked = new ObjectId();
        discussionReplyDto.setDiscussionId(discussionIdMocked.toHexString());
        when(this.discussionReplyRepositoryMocked.updateReply(reply, replyIdMocked, discussionIdMocked)).thenReturn(true);
        this.discussionReplyImplSpy.saveOrUpdate(discussionReplyDto);
        ArgumentCaptor<String> replyArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).updateReply(replyArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is updated reply", replyArgumentCaptor.getValue());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void incrementLikes_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        ObjectId userIdMocked = new ObjectId();
        when(this.discussionReplyRepositoryMocked.incrementLikes(replyIdMocked, discussionIdMocked)).thenReturn(301);
        this.discussionReplyImplSpy.incrementLikes(replyIdMocked.toHexString(), discussionIdMocked.toHexString(), userIdMocked.toHexString());
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).incrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void decrementLikes_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        ObjectId userIdMocked = new ObjectId();
        when(this.discussionReplyRepositoryMocked.decrementLikes(replyIdMocked, discussionIdMocked)).thenReturn(299);
        this.discussionReplyImplSpy.decrementLikes(replyIdMocked.toHexString(), discussionIdMocked.toHexString(), userIdMocked.toHexString());
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).decrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }

    @Test
    void delete_PassingObjectToRepositoryLayer() {
        ObjectId replyIdMocked = new ObjectId();
        ObjectId discussionIdMocked = new ObjectId();
        when(this.discussionReplyRepositoryMocked.delete(replyIdMocked, discussionIdMocked)).thenReturn(true);
        this.discussionReplyImplSpy.delete(replyIdMocked.toHexString(), discussionIdMocked.toHexString());
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).delete(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());
    }


}