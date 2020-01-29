package com.hexlindia.drool.discussion.data.repository.impl;

import com.hexlindia.drool.discussion.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion.view.DiscussionReplyCardView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals("1", discussionReplyCardView.getDiscussionReplyView().getReplyId());
        assertEquals("1", discussionReplyCardView.getDiscussionReplyView().getDiscussionTopicId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getReply());
        assertEquals("3", discussionReplyCardView.getDiscussionReplyView().getUserId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getDatePosted());
        assertEquals("2.4k", discussionReplyCardView.getDiscussionReplyView().getLikes());
        assertEquals("3", discussionReplyCardView.getUserProfileCardView().getUserId());
        assertEquals("sonam31", discussionReplyCardView.getUserProfileCardView().getUsername());
    }

    @Test
    void getDicussionTopicCardView() {
        DiscussionTopicCardView discussionTopicCardView = discussionViewRepository.getDiscussionTopicCardView(1L);
        assertNotNull(discussionTopicCardView);
        assertEquals("1", discussionTopicCardView.getDiscussionTopicView().getTopicId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getTopic());
        assertEquals("1", discussionTopicCardView.getDiscussionTopicView().getUserId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDatePosted());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDateLastActive());
        assertEquals("154.5k", discussionTopicCardView.getDiscussionTopicView().getViews());
        assertEquals("12.7k", discussionTopicCardView.getDiscussionTopicView().getLikes());
        assertEquals("234", discussionTopicCardView.getDiscussionTopicView().getReplies());
        assertEquals("1", discussionTopicCardView.getUserProfileCardView().getUserId());
        assertEquals("priyanka11", discussionTopicCardView.getUserProfileCardView().getUsername());
    }
}