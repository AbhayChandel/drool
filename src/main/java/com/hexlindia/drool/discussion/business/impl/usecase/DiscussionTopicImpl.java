package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.common.util.DateTimeUtil;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopicUserLike;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicActivityEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import com.hexlindia.drool.discussion.data.repository.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import com.hexlindia.drool.discussion.to.mapper.DiscussionTopicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Component
@Slf4j
public class DiscussionTopicImpl implements DiscussionTopic {

    private final DiscussionTopicRepository discussionTopicRepository;
    private final DiscussionTopicMapper discussionTopicMapper;
    private final DiscussionTopicUserLike discussionTopicUserLike;

    @Autowired
    public DiscussionTopicImpl(DiscussionTopicRepository discussionTopicRepository, DiscussionTopicMapper discussionTopicMapper, DiscussionTopicUserLike discussionTopicUserLike) {
        this.discussionTopicRepository = discussionTopicRepository;
        this.discussionTopicMapper = discussionTopicMapper;
        this.discussionTopicUserLike = discussionTopicUserLike;
    }

    @Override
    public DiscussionTopicTo post(DiscussionTopicTo discussionTopicTo) {
        DiscussionTopicEntity discussionTopicEntity = discussionTopicRepository.save(discussionTopicMapper.toEntity(discussionTopicTo));
        log.debug("DiscussionTopic: '{}', id: '{}' created", discussionTopicEntity.getTopic(), discussionTopicEntity.getId());
        DiscussionTopicActivityEntity discussionTopicActivityEntity = new DiscussionTopicActivityEntity(discussionTopicEntity.getId());
        Timestamp timestamp = DateTimeUtil.getCurrentTimestamp();
        discussionTopicActivityEntity.setDatePosted(timestamp);
        discussionTopicActivityEntity.setDateLastActive(timestamp);
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicEntity = discussionTopicRepository.save(discussionTopicEntity);
        log.debug("Discussion id: '{}' activity row created", discussionTopicEntity.getId());
        return discussionTopicMapper.toTransferObject(discussionTopicEntity);
    }

    @Override
    public DiscussionTopicTo findById(Long id) {
        return discussionTopicMapper.toTransferObject(findInRepository("Topic search", id));
    }

    @Override
    public DiscussionTopicTo updateTopicTitle(DiscussionTopicTo discussionTopicTo) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic title update", discussionTopicTo.getId());
        discussionTopicEntity.setTopic(discussionTopicTo.getTopic());
        return discussionTopicMapper.toTransferObject(discussionTopicRepository.save(discussionTopicEntity));
    }

    @Override
    public void incrementViewsByOne(Long id) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic views increment", id);
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setViews(discussionTopicActivityEntity.getViews() + 1);
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);
    }

    @Override
    @Transactional
    public void incrementLikesByOne(ActivityTo activityTo) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic likes increment", activityTo.getPostId());
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setLikes(discussionTopicActivityEntity.getLikes() + 1);
        discussionTopicActivityEntity.setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);

        discussionTopicUserLike.save(new DiscussionTopicUserLikeId(activityTo.getCurrentUserId(), activityTo.getPostId()));
    }

    @Override
    @Transactional
    public void decrementLikesByOne(ActivityTo activityTo) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic likes decrement", activityTo.getPostId());
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setLikes(discussionTopicActivityEntity.getLikes() - 1);
        discussionTopicActivityEntity.setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);

        discussionTopicUserLike.remove(new DiscussionTopicUserLikeId(activityTo.getCurrentUserId(), activityTo.getPostId()));
    }

    @Override
    public void incrementRepliesByOne(Long id) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic replies increment", id);
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setReplies(discussionTopicActivityEntity.getReplies() + 1);
        discussionTopicActivityEntity.setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);
    }

    @Override
    public void decrementRepliesByOne(Long id) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Topic replies decrement", id);
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setReplies(discussionTopicActivityEntity.getReplies() - 1);
        discussionTopicActivityEntity.setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);
    }

    @Override
    public void setLastDateActiveToNow(Long id) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Update last active date", id);
        DiscussionTopicActivityEntity discussionTopicActivityEntity = discussionTopicEntity.getDiscussionTopicActivityEntity();
        discussionTopicActivityEntity.setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicEntity.setDiscussionTopicActivityEntity(discussionTopicActivityEntity);
        discussionTopicRepository.save(discussionTopicEntity);
    }

    @Override
    public void saveReply(DiscussionReplyEntity discussionReplyEntity, Long discussionTopicId) {
        DiscussionTopicEntity discussionTopicEntity = findInRepository("Save reply", discussionTopicId);
        discussionTopicEntity.getDiscussionReplyEntityList().add(discussionReplyEntity);
        discussionTopicEntity.getDiscussionTopicActivityEntity().setReplies(discussionTopicEntity.getDiscussionTopicActivityEntity().getReplies() + 1);
        discussionTopicEntity.getDiscussionTopicActivityEntity().setDateLastActive(DateTimeUtil.getCurrentTimestamp());
        discussionTopicRepository.save(discussionTopicEntity);
        discussionReplyEntity.setDiscussionTopicEntity(discussionTopicEntity);
    }

    private DiscussionTopicEntity findInRepository(String action, Long id) {
        Optional<DiscussionTopicEntity> discussionTopicEntityOptional = discussionTopicRepository.findById(id);
        if (discussionTopicEntityOptional.isPresent()) {
            return discussionTopicEntityOptional.get();
        }
        StringBuilder errorMessage = new StringBuilder(action);
        errorMessage.append(" failed. Discussion topic with id " + id + " not found");
        throw new DiscussionTopicNotFoundException(errorMessage.toString());
    }
}
