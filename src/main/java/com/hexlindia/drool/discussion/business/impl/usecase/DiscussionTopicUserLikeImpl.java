package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopicUserLike;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicUserLikeRepository;
import com.hexlindia.drool.discussion.exception.DiscussionTopicUserLikeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiscussionTopicUserLikeImpl implements DiscussionTopicUserLike {

    private DiscussionTopicUserLikeRepository discussionTopicUserLikeRepository;

    @Autowired
    public DiscussionTopicUserLikeImpl(DiscussionTopicUserLikeRepository discussionTopicUserLikeRepository) {
        this.discussionTopicUserLikeRepository = discussionTopicUserLikeRepository;
    }

    @Override
    public void save(DiscussionTopicUserLikeId discussionTopicUserLikeId) {
        discussionTopicUserLikeRepository.save(new DiscussionTopicUserLikeEntity(discussionTopicUserLikeId));
    }

    @Override
    public void remove(DiscussionTopicUserLikeId discussionTopicUserLikeId) {
        Optional<DiscussionTopicUserLikeEntity> discussionTopicUserLikeEntityOptional = discussionTopicUserLikeRepository.findById(discussionTopicUserLikeId);
        if (discussionTopicUserLikeEntityOptional.isPresent()) {
            discussionTopicUserLikeRepository.deleteById(discussionTopicUserLikeEntityOptional.get().getId());
        } else {
            throw new DiscussionTopicUserLikeNotFoundException("Discussion user like Entry not found for User Id: " + discussionTopicUserLikeId.getUserId() + " and Topic Id: " + discussionTopicUserLikeId.getTopicId());
        }

    }
}
