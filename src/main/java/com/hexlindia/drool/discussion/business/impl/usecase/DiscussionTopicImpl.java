package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionTopicDtoDocMapper;
import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiscussionTopicImpl implements DiscussionTopic {

    private final DiscussionTopicRepository discussionTopicRepository;
    private final DiscussionTopicDtoDocMapper discussionTopicDtoDocMapper;
    private final UserActivity userActivity;
    private final ActivityFeed activityFeed;

    @Override
    public DiscussionTopicDto post(DiscussionTopicDto discussionTopicDto) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicDtoDocMapper.toDoc(discussionTopicDto);
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);
        discussionTopicDoc.setDateLastActive(datePosted);
        discussionTopicDoc.setActive(true);
        discussionTopicDoc = discussionTopicRepository.save(discussionTopicDoc);
        if (discussionTopicDoc.getId() != null) {
            userActivity.addDiscussion(discussionTopicDoc);
            activityFeed.addDiscussion(discussionTopicDoc);
            return discussionTopicDtoDocMapper.toDto(discussionTopicDoc);
        }
        log.error("Discussion not saved");
        return null;
    }

    @Override
    public DiscussionTopicDto findById(String id) {
        Optional<DiscussionTopicDoc> discussionTopicDocOptional = discussionTopicRepository.findById(new ObjectId(id));
        if (discussionTopicDocOptional.isPresent()) {
            return discussionTopicDtoDocMapper.toDto(discussionTopicDocOptional.get());
        }
        throw new DiscussionTopicNotFoundException("Discussion topic with Id " + id + " not found");
    }

    @Override
    public boolean updateTopicTitle(String title, String id) {
        return discussionTopicRepository.updateTopicTitle(title, new ObjectId(id));
    }

    @Override
    public String incrementViews(String id) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.incrementViews(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getViews());
        }
        return null;
    }

    @Override
    public String incrementLikes(String id, String userId) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.incrementLikes(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }

    @Override
    public String decrementLikes(String id, String userId) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.decrementLikes(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }
}
