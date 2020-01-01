package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicActivityEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
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
        assertEquals(5L, discussionTopicEntityRetrieved.getUserId());
        assertEquals(1, discussionTopicEntityRetrieved.getDiscussionReplyEntityList().size());
    }

    @Test
    void testSave() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity("Dandruff ke liye sabse acha shampoo kaun sa hai", 3L, true);
        DiscussionTopicEntity discussionTopicEntityRetrieved = discussionTopicRepository.save(discussionTopicEntity);

        DiscussionTopicActivityEntity discussionTopicActivityEntity = new DiscussionTopicActivityEntity(discussionTopicEntityRetrieved.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        discussionTopicActivityEntity.setDatePosted(timestamp);
        discussionTopicActivityEntity.setDateLastActive(timestamp);
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);

        Optional<DiscussionTopicEntity> discussionTopicEntityRetrievedOptional = this.discussionTopicRepository.findById(2L);
        assertTrue(discussionTopicEntityRetrievedOptional.isPresent());
        discussionTopicEntityRetrieved = discussionTopicEntityRetrievedOptional.get();
        assertEquals("Dandruff ke liye sabse acha shampoo kaun sa hai", discussionTopicEntityRetrieved.getTopic());
        assertEquals(3L, discussionTopicEntityRetrieved.getUserId());
        DiscussionTopicActivityEntity discussionTopicActivityEntityRetrieved = discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity();
        assertNotNull(discussionTopicActivityEntityRetrieved);
        assertEquals(2L, discussionTopicActivityEntityRetrieved.getDiscussionTopicId());
        assertEquals(timestamp, discussionTopicActivityEntityRetrieved.getDatePosted());
        assertEquals(timestamp, discussionTopicActivityEntityRetrieved.getDateLastActive());
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
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setViews(55);
        discussionTopicEntityRetrieved.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(55, discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity().getViews());
    }

    @Test
    void testUpdateLikes() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setLikes(105);
        discussionTopicEntityRetrieved.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(105, discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity().getLikes());
    }

    @Test
    void testUpdateReplies() {
        DiscussionTopicEntity discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setReplies(25);
        discussionTopicEntityRetrieved.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntityRetrieved);
        discussionTopicEntityRetrieved = this.discussionTopicRepository.findById(1L).get();
        assertEquals(25, discussionTopicEntityRetrieved.getDiscussionTopicActivityEntity().getReplies());
    }

}