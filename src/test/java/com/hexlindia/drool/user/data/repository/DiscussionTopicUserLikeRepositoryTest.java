package com.hexlindia.drool.user.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.DiscussionTopicUserLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

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
        Optional<DiscussionTopicUserLikeEntity> userLikeEntity = discussionTopicUserLikeRepository.findById(discussionTopicUserLikeId);
        assertFalse(userLikeEntity.isPresent());

    }

    @Test
    void findById() {
        DiscussionTopicUserLikeId discussionTopicUserLikeId = new DiscussionTopicUserLikeId(6L, 6L);
        Optional<DiscussionTopicUserLikeEntity> userLikeEntity = discussionTopicUserLikeRepository.findById(discussionTopicUserLikeId);
        assertTrue(userLikeEntity.isPresent());
    }
}