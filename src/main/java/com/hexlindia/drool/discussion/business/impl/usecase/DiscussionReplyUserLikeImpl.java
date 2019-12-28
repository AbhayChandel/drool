package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReplyUserLike;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;
import com.hexlindia.drool.discussion.data.repository.DiscussionReplyUserLikeRepository;
import com.hexlindia.drool.discussion.exception.DiscussionReplyUserLikeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiscussionReplyUserLikeImpl implements DiscussionReplyUserLike {

    private final DiscussionReplyUserLikeRepository discussionReplyUserLikeRepository;

    @Autowired
    public DiscussionReplyUserLikeImpl(DiscussionReplyUserLikeRepository discussionReplyUserLikeRepository) {
        this.discussionReplyUserLikeRepository = discussionReplyUserLikeRepository;
    }

    @Override
    public void save(DiscussionReplyUserLikeId id) {
        this.discussionReplyUserLikeRepository.save(new DiscussionReplyUserLikeEntity(id));
    }

    @Override
    public void remove(DiscussionReplyUserLikeId id) {
        Optional<DiscussionReplyUserLikeEntity> discussionReplyUserLikeEntityOptional = discussionReplyUserLikeRepository.findById(id);
        if (discussionReplyUserLikeEntityOptional.isPresent()) {
            discussionReplyUserLikeRepository.deleteById(discussionReplyUserLikeEntityOptional.get().getId());
        } else {
            throw new DiscussionReplyUserLikeNotFoundException("Reply user like entry not found for User Id: " + id.getUserId() + " and Topic Id: " + id.getReplyId());
        }
    }
}
