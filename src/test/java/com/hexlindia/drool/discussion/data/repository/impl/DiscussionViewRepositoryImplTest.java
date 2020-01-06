package com.hexlindia.drool.discussion.data.repository.impl;

import com.hexlindia.drool.discussion.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion.view.DiscussionReplyCardView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscussionViewRepositoryImplTest {

    @Autowired
    DiscussionViewRepository discussionViewRepository;

    @Test
    void getDicussionReplyCardViews() {
        List<DiscussionReplyCardView> discussionReplyCardViewList = discussionViewRepository.getDicussionReplyCardViews(1L);
        assertNotNull(discussionReplyCardViewList);
        assertEquals(2, discussionReplyCardViewList.size());
        DiscussionReplyCardView discussionReplyCardView = discussionReplyCardViewList.get(0);
        assertEquals(1L, discussionReplyCardView.getDiscussionReplyView().getReplyId());
        assertEquals(1L, discussionReplyCardView.getDiscussionReplyView().getDiscussionTopicId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getReply());
        assertEquals(3L, discussionReplyCardView.getDiscussionReplyView().getUserId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getDatePosted());
        assertEquals(2, discussionReplyCardView.getDiscussionReplyView().getLikes());
        assertEquals(3L, discussionReplyCardView.getUserProfileCardView().getUserId());
        assertEquals("sonam31", discussionReplyCardView.getUserProfileCardView().getUsername());
    }

    @Test
    void getDicussionTopicCardView() {
        DiscussionTopicCardView discussionTopicCardView = discussionViewRepository.getDiscussionTopicCardView(1L);
        assertNotNull(discussionTopicCardView);
        assertEquals(1L, discussionTopicCardView.getDiscussionTopicView().getTopicId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getTopic());
        assertEquals(1L, discussionTopicCardView.getDiscussionTopicView().getUserId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDatePosted());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDateLastActive());
        assertTrue(discussionTopicCardView.getDiscussionTopicView().getViews() >= 15);
        assertEquals(12, discussionTopicCardView.getDiscussionTopicView().getLikes());
        assertEquals(2, discussionTopicCardView.getDiscussionTopicView().getReplies());
        assertEquals(1L, discussionTopicCardView.getUserProfileCardView().getUserId());
        assertEquals("priyanka11", discussionTopicCardView.getUserProfileCardView().getUsername());
    }
}