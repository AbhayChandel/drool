package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.data.doc.ReplyRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyRepository;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionReplyDtoDocMapper;
import com.hexlindia.drool.discussion.dto.mapper.ReplyDtoToRefMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
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

    @Mock
    UserActivity userActivityMock;

    @Mock
    ActivityFeed activityFeedMock;

    @Mock
    PostRefMapper postRefMapperMock;

    @Mock
    ReplyDtoToRefMapper replyDtoToRefMapperMocked;

    @BeforeEach
    void setUp() {
        this.discussionReplyImplSpy = Mockito.spy(new DiscussionReplyImpl(discussionReplyRepositoryMocked, discussionReplyDtoDocMapperMocked, userActivityMock, postRefMapperMock, activityFeedMock, replyDtoToRefMapperMocked));
    }

    @Test
    void save_passingObject() {
        DiscussionReplyDoc discussionReplyDocMocked = new DiscussionReplyDoc();
        discussionReplyDocMocked.setReply("This is a test reply");
        LocalDateTime datePosted = LocalDateTime.now();
        discussionReplyDocMocked.setDatePosted(datePosted);
        discussionReplyDocMocked.setLikes(50);
        ObjectId replyId = ObjectId.get();
        discussionReplyDocMocked.setId(replyId);
        ObjectId discussionIdMocked = new ObjectId();
        ObjectId userId = new ObjectId();
        discussionReplyDocMocked.setUserRef(new UserRef(userId, "shabana"));
        when(this.discussionReplyDtoDocMapperMocked.toDoc(any())).thenReturn(discussionReplyDocMocked);
        when(this.discussionReplyRepositoryMocked.saveReply(discussionReplyDocMocked, discussionIdMocked)).thenReturn(true);
        when(this.userActivityMock.add(any(), any(), any())).thenReturn(null);
        PostRef postRef = new PostRef();
        postRef.setId(discussionIdMocked);
        postRef.setTitle("This is a test discussion topic");
        when(this.postRefMapperMock.toDoc(any())).thenReturn(postRef);
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        PostRefDto postRefDto = new PostRefDto();
        postRefDto.setId(discussionIdMocked.toHexString());
        discussionReplyDto.setPostRefDto(postRefDto);
        ReplyRef replyRef = new ReplyRef();
        replyRef.setId(replyId);
        replyRef.setReply("This is a test reply");
        when(this.replyDtoToRefMapperMocked.toReplyRef(any())).thenReturn(replyRef);
        this.discussionReplyImplSpy.saveOrUpdate(discussionReplyDto);
        ArgumentCaptor<DiscussionReplyDoc> discussionReplyDocArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyDoc.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).saveReply(discussionReplyDocArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is a test reply", discussionReplyDocArgumentCaptor.getValue().getReply());
        assertEquals(50, discussionReplyDocArgumentCaptor.getValue().getLikes());
        assertEquals(userId, discussionReplyDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", discussionReplyDocArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, postRefArgumentCaptor.getValue().getParentPost().getId());
        assertEquals(replyId, postRefArgumentCaptor.getValue().getId());

        ArgumentCaptor<ObjectId> discussionIdArgumentCaptorActivityFeed = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<FeedDocField> feedDocFieldsArgumentCaptorActivityFeed = ArgumentCaptor.forClass(FeedDocField.class);
        ArgumentCaptor<Integer> incrementArgumentCaptorActivityFeed = ArgumentCaptor.forClass(Integer.class);
        verify(this.activityFeedMock, times(1)).incrementDecrementField(discussionIdArgumentCaptorActivityFeed.capture(), feedDocFieldsArgumentCaptorActivityFeed.capture(), incrementArgumentCaptorActivityFeed.capture());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptorActivityFeed.getValue());
        assertEquals(FeedDocField.comments, feedDocFieldsArgumentCaptorActivityFeed.getValue());
        assertEquals(1, incrementArgumentCaptorActivityFeed.getValue());


    }

    @Test
    void update_passingObject() {
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        String reply = "This is updated reply";
        discussionReplyDto.setReply(reply);
        ObjectId replyIdMocked = new ObjectId();
        discussionReplyDto.setId(replyIdMocked.toHexString());
        ObjectId discussionIdMocked = new ObjectId();
        PostRefDto postRefDto = new PostRefDto();
        postRefDto.setId(discussionIdMocked.toHexString());
        postRefDto.setTitle("This is a test discussion topic");
        discussionReplyDto.setPostRefDto(postRefDto);
        ObjectId userIdMocked = ObjectId.get();
        discussionReplyDto.setUserRefDto(new UserRefDto(userIdMocked.toHexString(), "shabana"));
        when(this.discussionReplyRepositoryMocked.updateReply(reply, replyIdMocked, discussionIdMocked)).thenReturn(true);
        PostRef postRefMock = new PostRef();
        postRefMock.setId(discussionIdMocked);
        postRefMock.setTitle("This is a test discussion topic");
        when(this.postRefMapperMock.toDoc(any())).thenReturn(postRefMock);
        ReplyRef replyRef = new ReplyRef();
        when(this.replyDtoToRefMapperMocked.toReplyRef(any())).thenReturn(replyRef);
        this.discussionReplyImplSpy.saveOrUpdate(discussionReplyDto);
        ArgumentCaptor<String> replyArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).updateReply(replyArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("This is updated reply", replyArgumentCaptor.getValue());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).update(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userIdMocked, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(replyIdMocked, postRefArgumentCaptor.getValue().getId());
    }

    @Test
    void incrementLikes_PassingObjectToRepositoryLayer() {
        DiscussionReplyDto discussionReplyDtoMocked = new DiscussionReplyDto();
        ObjectId replyId = ObjectId.get();
        discussionReplyDtoMocked.setId(replyId.toHexString());
        String reply = "This is going to be a great reply";
        discussionReplyDtoMocked.setReply(reply);
        ObjectId postId = ObjectId.get();
        discussionReplyDtoMocked.setPostRefDto(new PostRefDto(postId.toHexString(), "This is a test discussion", PostType.discussion, PostMedium.text, null));
        ObjectId userId = ObjectId.get();
        discussionReplyDtoMocked.setUserRefDto(new UserRefDto(userId.toHexString(), "Arpit"));
        when(this.discussionReplyRepositoryMocked.incrementLikes(any(), any())).thenReturn(301);
        ReplyRef replyRef = new ReplyRef();
        when(this.replyDtoToRefMapperMocked.toReplyRef(any())).thenReturn(replyRef);
        this.discussionReplyImplSpy.incrementLikes(discussionReplyDtoMocked);
        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).incrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyId, replyIdArgumentCaptor.getValue());
        assertEquals(postId, discussionIdArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(replyId, postRefArgumentCaptor.getValue().getId());
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
    void delete_PassingToRepositoryAndUserActivity() {
        ObjectId replyIdMocked = ObjectId.get();
        ObjectId discussionIdMocked = ObjectId.get();
        ObjectId userIdMocked = ObjectId.get();

        when(this.discussionReplyRepositoryMocked.delete(replyIdMocked, discussionIdMocked)).thenReturn(true);
        when(this.userActivityMock.delete(any(), any(), any())).thenReturn(null);
        this.discussionReplyImplSpy.delete(replyIdMocked.toHexString(), discussionIdMocked.toHexString(), userIdMocked.toHexString());

        ArgumentCaptor<ObjectId> replyIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> discussionIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionReplyRepositoryMocked, times(1)).delete(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals(replyIdMocked, replyIdArgumentCaptor.getValue());
        assertEquals(discussionIdMocked, discussionIdArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userIdMocked, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(replyIdMocked, postRefArgumentCaptor.getValue().getId());
    }


}