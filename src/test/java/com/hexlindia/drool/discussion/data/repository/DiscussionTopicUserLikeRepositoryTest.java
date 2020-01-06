package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicUserLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class DiscussionTopicUserLikeRepositoryTest {

    @Autowired
    DiscussionTopicUserLikeRepository discussionTopicUserLikeRepository;

    @Test
    void deleteById() {
        DiscussionTopicUserLikeId discussionTopicUserLikeId = new DiscussionTopicUserLikeId(5L, 5L);
        discussionTopicUserLikeRepository.deleteById(discussionTopicUserLikeId);
        assertFalse(discussionTopicUserLikeRepository.findById(discussionTopicUserLikeId).isPresent());
    }

    @Test
    void findById() {
        DiscussionTopicUserLikeId discussionTopicUserLikeId = new DiscussionTopicUserLikeId(6L, 6L);
        assertTrue(discussionTopicUserLikeRepository.findById(discussionTopicUserLikeId).isPresent());
    }

}