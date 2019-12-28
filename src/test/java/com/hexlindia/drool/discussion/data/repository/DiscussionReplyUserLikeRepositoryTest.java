package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class DiscussionReplyUserLikeRepositoryTest {

    @Autowired
    DiscussionReplyUserLikeRepository discussionReplyUserLikeRepository;

    @Test
    void deleteById() {
        DiscussionReplyUserLikeId discussionReplyUserLikeId = new DiscussionReplyUserLikeId(7L, 7L);
        discussionReplyUserLikeRepository.deleteById(discussionReplyUserLikeId);
        assertFalse(discussionReplyUserLikeRepository.findById(discussionReplyUserLikeId).isPresent());
    }

    @Test
    void findById() {
        assertTrue(discussionReplyUserLikeRepository.findById(new DiscussionReplyUserLikeId(8L, 8L)).isPresent());
    }
}