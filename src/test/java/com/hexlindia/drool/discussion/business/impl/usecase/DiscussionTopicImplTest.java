package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionTopicDtoDocMapper;
import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionTopicImplTest {

    private DiscussionTopicImpl discussionTopicImplSpy;

    @Mock
    private DiscussionTopicDtoDocMapper discussionTopicDtoDocMapperMocked;

    @Mock
    private DiscussionTopicRepository discussionTopicRepository;

    @Autowired
    private DiscussionTopicDtoDocMapper discussionTopicDtoDocMapper;

    @Mock
    private UserActivity userActivityMock;

    @Mock
    private ActivityFeed activityFeedMock;

    @Mock
    private UserAccount userAccountMock;

    @Mock
    private UserRefMapper userRefMapperMock;

    @BeforeEach
    void setUp() {
        this.discussionTopicImplSpy = Mockito.spy(new DiscussionTopicImpl(this.discussionTopicRepository, this.discussionTopicDtoDocMapperMocked, userActivityMock, activityFeedMock, userAccountMock, userRefMapperMock));
    }

    @Test
    void post_PassingObjectToRepositoryLayer() {
        DiscussionTopicDoc discussionTopicDocMocked = new DiscussionTopicDoc();
        discussionTopicDocMocked.setTitle("THis is a test discusion title");
        discussionTopicDocMocked.setLikes(0);
        discussionTopicDocMocked.setViews(0);
        discussionTopicDocMocked.setActive(true);
        when(this.discussionTopicDtoDocMapperMocked.toDoc(any())).thenReturn(discussionTopicDocMocked);
        when(this.discussionTopicRepository.save(any())).thenReturn(discussionTopicDocMocked);
        this.discussionTopicImplSpy.post(null);
        ArgumentCaptor<DiscussionTopicDoc> discussionTopicDocArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicDoc.class);
        verify(this.discussionTopicRepository, times(1)).save(discussionTopicDocArgumentCaptor.capture());
        assertEquals("THis is a test discusion title", discussionTopicDocArgumentCaptor.getValue().getTitle());
        assertEquals(0, discussionTopicDocArgumentCaptor.getValue().getLikes());
        assertEquals(0, discussionTopicDocArgumentCaptor.getValue().getViews());
        assertTrue(discussionTopicDocArgumentCaptor.getValue().isActive());
    }

    @Test
    void post_PassingObjectToUserActivityAndActivityComponent() {
        DiscussionTopicDoc discussionTopicDocMocked = new DiscussionTopicDoc();
        discussionTopicDocMocked.setTitle("THis is a test discusion title");
        discussionTopicDocMocked.setLikes(0);
        discussionTopicDocMocked.setViews(0);
        discussionTopicDocMocked.setActive(true);
        ObjectId userId = ObjectId.get();
        discussionTopicDocMocked.setUserRef(new UserRef(userId, ""));
        ObjectId discussionId = ObjectId.get();
        discussionTopicDocMocked.setId(discussionId);
        when(this.discussionTopicDtoDocMapperMocked.toDoc(any())).thenReturn(discussionTopicDocMocked);
        when(this.discussionTopicRepository.save(any())).thenReturn(discussionTopicDocMocked);
        this.discussionTopicImplSpy.post(null);

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(discussionId, postRefArgumentCaptor.getValue().getId());
        assertEquals("THis is a test discusion title", postRefArgumentCaptor.getValue().getTitle());
        assertEquals(PostType.discussion, postRefArgumentCaptor.getValue().getType());
        assertEquals(PostFormat.article, postRefArgumentCaptor.getValue().getMedium());

        ArgumentCaptor<DiscussionTopicDoc> discussionTopicDocArgumentCaptorActivityFeed = ArgumentCaptor.forClass(DiscussionTopicDoc.class);
        verify(this.activityFeedMock, times(1)).addDiscussion(discussionTopicDocArgumentCaptorActivityFeed.capture());
        assertEquals("THis is a test discusion title", discussionTopicDocArgumentCaptorActivityFeed.getValue().getTitle());
        assertEquals(0, discussionTopicDocArgumentCaptorActivityFeed.getValue().getLikes());
        assertEquals(0, discussionTopicDocArgumentCaptorActivityFeed.getValue().getViews());
        assertTrue(discussionTopicDocArgumentCaptorActivityFeed.getValue().isActive());
        assertEquals(discussionId, discussionTopicDocArgumentCaptorActivityFeed.getValue().getId());
    }

    @Test
    void findById_testPassingEntityToRepository() {
        ObjectId id = new ObjectId();
        when(this.discussionTopicRepository.findById(id)).thenReturn(Optional.of(new DiscussionTopicDoc()));
        discussionTopicImplSpy.findById(id.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(discussionTopicRepository, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void findById_docFoundResponse() {
        DiscussionTopicDoc discussionTopicDocMocked = new DiscussionTopicDoc();
        discussionTopicDocMocked.setTitle("THis is a test discusion title");
        discussionTopicDocMocked.setLikes(10);
        discussionTopicDocMocked.setViews(20);
        discussionTopicDocMocked.setActive(true);
        Optional<DiscussionTopicDoc> discussionTopicDocOptional = Optional.of(discussionTopicDocMocked);
        when(this.discussionTopicRepository.findById(any())).thenReturn(discussionTopicDocOptional);
        when(this.discussionTopicDtoDocMapperMocked.toDto(discussionTopicDocMocked)).thenReturn(discussionTopicDtoDocMapper.toDto(discussionTopicDocMocked));
        DiscussionTopicDto discussionTopicDtoReturned = discussionTopicImplSpy.findById(ObjectId.get().toHexString());

        assertEquals("THis is a test discusion title", discussionTopicDtoReturned.getTitle());
        assertEquals("10", discussionTopicDtoReturned.getLikes());
        assertEquals("20", discussionTopicDtoReturned.getViews());
    }

    @Test
    void findById_docNotFoundException() {
        when(this.discussionTopicRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DiscussionTopicNotFoundException.class, () -> {
            discussionTopicImplSpy.findById(ObjectId.get().toHexString());
        });
    }

    @Test
    void updateTopicTitle_testPassingEntityToRepository() {
        ObjectId id = ObjectId.get();
        String title = "This is a test title";
        DiscussionTopicDto discussionTopicDto = new DiscussionTopicDto();
        discussionTopicDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), ""));
        discussionTopicDto.setId(id.toHexString());
        discussionTopicDto.setTitle(title);
        when(this.discussionTopicRepository.updateTopicTitle(title, id)).thenReturn(true);
        discussionTopicImplSpy.updateTopicTitle(discussionTopicDto);
        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(discussionTopicRepository, times(1)).updateTopicTitle(titleArgumentCaptor.capture(), idArgumentCaptor.capture());
        assertEquals(title, titleArgumentCaptor.getValue());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void incrementViews_passedToRepository() {
        ObjectId id = new ObjectId();
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setViews(1234);
        when(this.discussionTopicRepository.incrementViews(id)).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.incrementViews(id.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicRepository, times(1)).incrementViews(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void incrementLikes_passedToRepository() {
        ObjectId discussionId = ObjectId.get();
        DiscussionTopicDto discussionTopicDto = new DiscussionTopicDto();
        discussionTopicDto.setId(discussionId.toHexString());
        ObjectId userId = ObjectId.get();
        discussionTopicDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setId(discussionId);
        discussionTopicDoc.setLikes(12344);

        when(this.discussionTopicRepository.incrementLikes(any())).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.incrementLikes(discussionTopicDto);

        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicRepository, times(1)).incrementLikes(idArgumentCaptor.capture());
        assertEquals(discussionId, idArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(discussionId, postRefArgumentCaptor.getValue().getId());
    }

    @Test
    void decrementLikes_passedToRepository() {
        ObjectId discussionId = ObjectId.get();
        DiscussionTopicDto discussionTopicDto = new DiscussionTopicDto();
        discussionTopicDto.setId(discussionId.toHexString());
        ObjectId userId = ObjectId.get();
        discussionTopicDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setId(discussionId);
        discussionTopicDoc.setLikes(12344);

        when(this.discussionTopicRepository.decrementLikes(any())).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.decrementLikes(discussionTopicDto);

        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicRepository, times(1)).decrementLikes(idArgumentCaptor.capture());
        assertEquals(discussionId, idArgumentCaptor.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(discussionId, postRefArgumentCaptor.getValue().getId());
    }

}