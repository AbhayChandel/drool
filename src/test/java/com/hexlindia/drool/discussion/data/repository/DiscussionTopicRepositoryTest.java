package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiscussionTopicRepositoryTest {

    @Autowired
    DiscussionTopicRepository discussionTopicRepository;

    @Test
    void testFind() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals("Are Loreal lip colors better than Lakme or is it the other way around", discussionTopicEntityRetrieved.getTopic());
        assertEquals(1L, discussionTopicEntityRetrieved.getUserId());
        assertEquals(2, discussionTopicEntityRetrieved.getDiscussionReplyEntityList().size());
    }

    @Test
    void testSave() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity("Dandruff ke liye sabse acha shampoo kaun sa hai", 3L, true);
        LocalDateTime timestamp = LocalDateTime.now();
        discussionTopicEntity.setDatePosted(timestamp);
        discussionTopicEntity.setDateLastActive(timestamp);
        discussionTopicRepository.save(discussionTopicEntity);

        Optional<DiscussionTopicEntity> discussionTopicEntityRetrievedOptional = this.discussionTopicRepository.findById(2L);
        assertTrue(discussionTopicEntityRetrievedOptional.isPresent());
        DiscussionTopicEntity discussionTopicEntityRetrieved = discussionTopicEntityRetrievedOptional.get();
        assertEquals("Dandruff ke liye sabse acha shampoo kaun sa hai", discussionTopicEntityRetrieved.getTopic());
        assertEquals(3L, discussionTopicEntityRetrieved.getUserId());
        assertEquals(timestamp, discussionTopicEntity.getDatePosted());
        assertEquals(timestamp, discussionTopicEntity.getDateLastActive());
    }

    @Test
    void testUpdateActive() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        discussionTopicEntityRetrieved.setActive(false);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertFalse(discussionTopicEntityRetrieved.isActive());
    }

    @Test
    void testUpdateViews() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        discussionTopicEntityRetrieved.setViews(55);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(55, discussionTopicEntityRetrieved.getViews());
    }

    @Test
    void testUpdateLikes() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        discussionTopicEntityRetrieved.setLikes(105);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(105, discussionTopicEntityRetrieved.getLikes());
    }

    @Test
    void testUpdateReplies() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        discussionTopicEntityRetrieved.setReplies(25);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(25, discussionTopicEntityRetrieved.getReplies());
    }

}