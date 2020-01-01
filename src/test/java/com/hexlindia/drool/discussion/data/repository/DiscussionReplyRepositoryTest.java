package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyActivityEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiscussionReplyRepositoryTest {

    @Autowired
    DiscussionReplyRepository discussionReplyRepository;

    @Test
    void testFind() {
        DiscussionReplyEntity discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        assertEquals("Yes, Loreal is better than Lakme", discussionReplyEntityRetrieved.getReply());
        assertEquals(4L, discussionReplyEntityRetrieved.getUserId());
    }

    @Test
    void testSave() {
        DiscussionReplyEntity discussionReplyEntityMocked = new DiscussionReplyEntity("No, it is not better", 3L);
        DiscussionReplyEntity discussionReplyEntityRetrieved = discussionReplyRepository.save(discussionReplyEntityMocked);

        DiscussionReplyActivityEntity discussionReplyActivityEntityMocked = new DiscussionReplyActivityEntity(discussionReplyEntityRetrieved.getId());

        discussionReplyEntityMocked.setDiscussionReplyActivityEntity(discussionReplyActivityEntityMocked);
        discussionReplyRepository.save(discussionReplyEntityMocked);

        Optional<DiscussionReplyEntity> discussionReplyEntityRetrievedOptional = this.discussionReplyRepository.findById(3L);
        assertTrue(discussionReplyEntityRetrievedOptional.isPresent());
        discussionReplyEntityRetrieved = discussionReplyEntityRetrievedOptional.get();
        assertEquals("No, it is not better", discussionReplyEntityRetrieved.getReply());
        assertEquals(3L, discussionReplyEntityRetrieved.getUserId());
        DiscussionReplyActivityEntity discussionReplyActivityEntityRetrieved = discussionReplyEntityRetrieved.getDiscussionReplyActivityEntity();
        assertNotNull(discussionReplyActivityEntityRetrieved);
        assertEquals(3L, discussionReplyActivityEntityRetrieved.getDiscussionTopicId());
    }

    @Test
    void testUpdateActive() {
        DiscussionReplyEntity discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        discussionReplyEntityRetrieved.setActive(false);
        discussionReplyRepository.save(discussionReplyEntityRetrieved);
        discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        assertFalse(discussionReplyEntityRetrieved.isActive());
    }

    @Test
    void testUpdateLikes() {
        DiscussionReplyEntity discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        DiscussionReplyActivityEntity discussionReplyActivityEntityRetrieved = discussionReplyEntityRetrieved.getDiscussionReplyActivityEntity();
        discussionReplyActivityEntityRetrieved.setLikes(105);
        discussionReplyEntityRetrieved.setDiscussionReplyActivityEntity(discussionReplyActivityEntityRetrieved);
        discussionReplyRepository.save(discussionReplyEntityRetrieved);
        discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        assertEquals(105, discussionReplyEntityRetrieved.getDiscussionReplyActivityEntity().getLikes());
    }

}