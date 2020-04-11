package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicMongoRepository;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionTopicDtoDocMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiscussionTopicImplTest {

    private DiscussionTopicImpl discussionTopicImplSpy;

    @Mock
    private DiscussionTopicDtoDocMapper discussionTopicDtoDocMapper;

    @Mock
    DiscussionTopicMongoRepository discussionTopicMongoRepository;

    //START FIXING TEST FOR DISCUSSION TOPIC AND REPLY : REPOSITORY AND BUSINESS

    @BeforeEach
    void setUp() {
        this.discussionTopicImplSpy = Mockito.spy(new DiscussionTopicImpl(this.discussionTopicMongoRepository, this.discussionTopicDtoDocMapper));
    }

    @Test
    void post_PassingObjectToRepositoryLayer() {
        DiscussionTopicDoc discussionTopicDocMocked = new DiscussionTopicDoc();
        discussionTopicDocMocked.setTitle("THis is a test discusion title");
        discussionTopicDocMocked.setLikes(0);
        discussionTopicDocMocked.setViews(0);
        discussionTopicDocMocked.setActive(true);
        when(this.discussionTopicDtoDocMapper.toDoc(any())).thenReturn(discussionTopicDocMocked);
        when(this.discussionTopicMongoRepository.save(any())).thenReturn(discussionTopicDocMocked);
        this.discussionTopicImplSpy.post(null);
        ArgumentCaptor<DiscussionTopicDoc> discussionTopicDocArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicDoc.class);
        verify(this.discussionTopicMongoRepository, times(1)).save(discussionTopicDocArgumentCaptor.capture());
        assertEquals("THis is a test discusion title", discussionTopicDocArgumentCaptor.getValue().getTitle());
        assertEquals(0, discussionTopicDocArgumentCaptor.getValue().getLikes());
        assertEquals(0, discussionTopicDocArgumentCaptor.getValue().getViews());
        assertTrue(discussionTopicDocArgumentCaptor.getValue().isActive());
    }

    @Test
    void findById_testPassingEntityToRepository() {
        ObjectId id = new ObjectId();
        when(this.discussionTopicMongoRepository.findById(id)).thenReturn(new DiscussionTopicDoc());
        discussionTopicImplSpy.findById(id.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(discussionTopicMongoRepository, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void updateTopicTitle_testPassingEntityToRepository() {
        ObjectId id = new ObjectId();
        String title = "This is a test title";
        when(this.discussionTopicMongoRepository.updateTopicTitle(title, id)).thenReturn(true);
        discussionTopicImplSpy.updateTopicTitle(title, id.toHexString());
        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(discussionTopicMongoRepository, times(1)).updateTopicTitle(titleArgumentCaptor.capture(), idArgumentCaptor.capture());
        assertEquals(title, titleArgumentCaptor.getValue());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void incrementViews_passedToRepository() {
        ObjectId id = new ObjectId();
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setViews(1234);
        when(this.discussionTopicMongoRepository.incrementViews(id)).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.incrementViews(id.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicMongoRepository, times(1)).incrementViews(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void incrementLikes_passedToRepository() {
        ObjectId id = new ObjectId();
        ObjectId userId = new ObjectId();
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setLikes(12344);
        when(this.discussionTopicMongoRepository.incrementLikes(id)).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.incrementLikes(id.toHexString(), userId.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicMongoRepository, times(1)).incrementLikes(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    void decrementLikes_passedToRepository() {
        ObjectId id = new ObjectId();
        ObjectId userId = new ObjectId();
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setLikes(12344);
        when(this.discussionTopicMongoRepository.decrementLikes(id)).thenReturn(discussionTopicDoc);
        discussionTopicImplSpy.decrementLikes(id.toHexString(), userId.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.discussionTopicMongoRepository, times(1)).decrementLikes(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

}