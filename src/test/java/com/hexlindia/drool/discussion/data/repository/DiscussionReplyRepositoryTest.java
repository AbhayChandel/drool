package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyRepository;
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
        assertEquals(3L, discussionReplyEntityRetrieved.getUserId());
    }

    @Test
    void testSave() {
        discussionReplyRepository.save(new DiscussionReplyEntity("No, it is not better", 3L));

        Optional<DiscussionReplyEntity> discussionReplyEntityRetrievedOptional = this.discussionReplyRepository.findById(6L);
        assertTrue(discussionReplyEntityRetrievedOptional.isPresent());
        DiscussionReplyEntity discussionReplyEntityRetrieved = discussionReplyEntityRetrievedOptional.get();
        assertEquals("No, it is not better", discussionReplyEntityRetrieved.getReply());
        assertEquals(3L, discussionReplyEntityRetrieved.getUserId());
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
        discussionReplyEntityRetrieved.setLikes(105);
        discussionReplyRepository.save(discussionReplyEntityRetrieved);
        discussionReplyEntityRetrieved = this.discussionReplyRepository.findById(1L).get();
        assertEquals(105, discussionReplyEntityRetrieved.getLikes());
    }

}