package com.hexlindia.drool.discussion.data.repository.impl;

import com.hexlindia.drool.discussion.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion.view.DiscussionReplyCardView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DiscussionViewRepositoryImpl implements DiscussionViewRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public DiscussionTopicCardView getDiscussionTopicCardView(Long discussionTopicId) {
        String queryString = "Select * from discussion_topic_card_view where topicId = :discussionTopicId";
        Query query = entityManager.createNativeQuery(queryString, "discussionTopicCardView");
        query.setParameter("discussionTopicId", discussionTopicId);
        return (DiscussionTopicCardView) query.getSingleResult();
    }

    @Override
    public List<DiscussionReplyCardView> getDicussionReplyCardViews(Long discussionTopicId) {
        String queryString = "Select * from discussion_reply_card_view where discussionTopicId = :discussionTopicId";
        Query query = entityManager.createNativeQuery(queryString, "discussionReplyCardView");
        query.setParameter("discussionTopicId", discussionTopicId);
        return query.getResultList();
    }
}
