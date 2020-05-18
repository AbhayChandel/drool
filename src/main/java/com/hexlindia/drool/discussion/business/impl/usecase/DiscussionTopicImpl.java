package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionTopicDtoDocMapper;
import com.hexlindia.drool.discussion.exception.DiscussionTopicNotFoundException;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.user.dto.UserAccountDto;
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
    private final UserAccount userAccount;
    private final UserRefMapper userRefMapper;

    @Override
    public DiscussionTopicDto post(DiscussionTopicDto discussionTopicDto) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicDtoDocMapper.toDoc(discussionTopicDto);
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);
        discussionTopicDoc.setDateLastActive(datePosted);
        discussionTopicDoc.setActive(true);
        discussionTopicDoc = discussionTopicRepository.save(discussionTopicDoc);
        if (discussionTopicDoc.getId() != null) {
            userActivity.add(discussionTopicDoc.getUserRef().getId(), ActionType.post, new PostRef(discussionTopicDoc.getId(), discussionTopicDoc.getTitle(), PostType.discussion, PostMedium.text, discussionTopicDoc.getDatePosted()));
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
    public boolean updateTopicTitle(DiscussionTopicDto discussionTopicDto) {
        ObjectId postId = new ObjectId(discussionTopicDto.getId());
        String title = discussionTopicDto.getTitle();
        if (discussionTopicRepository.updateTopicTitle(title, postId)) {
            userActivity.update(new ObjectId(discussionTopicDto.getUserRefDto().getId()), ActionType.post, new PostRef(new ObjectId(discussionTopicDto.getId()), discussionTopicDto.getTitle(), PostType.discussion, PostMedium.text, null));
            activityFeed.setField(postId, FeedDocField.title, discussionTopicDto.getTitle());
            return true;
        }
        return false;
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
    public String incrementLikes(DiscussionTopicDto discussionTopicDto) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.incrementLikes(new ObjectId(discussionTopicDto.getId()));
        if (discussionTopicDoc != null) {
            userActivity.add(new ObjectId(discussionTopicDto.getUserRefDto().getId()), ActionType.like, new PostRef(discussionTopicDoc.getId(), discussionTopicDoc.getTitle(), PostType.discussion, PostMedium.text, null));
            activityFeed.incrementDecrementField(discussionTopicDoc.getId(), FeedDocField.likes, 1);
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }

    @Override
    public String decrementLikes(DiscussionTopicDto discussionTopicDto) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.decrementLikes(new ObjectId(discussionTopicDto.getId()));
        if (discussionTopicDoc != null) {
            userActivity.delete(new ObjectId(discussionTopicDto.getUserRefDto().getId()), ActionType.like, new PostRef(discussionTopicDoc.getId(), discussionTopicDoc.getTitle(), PostType.discussion, PostMedium.text, null));
            activityFeed.incrementDecrementField(discussionTopicDoc.getId(), FeedDocField.likes, -1);
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }

    @Override
    public DiscussionTopicDto changeOwnership(DiscussionTopicDto discussionTopicDto) {
        UserAccountDto userAccountDto = userAccount.findUser("Community");
        UserRef communityUser = new UserRef(new ObjectId(userAccountDto.getId()), userAccountDto.getUsername());
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.updateUser(new ObjectId(discussionTopicDto.getId()), communityUser, userRefMapper.toDoc(discussionTopicDto.getUserRefDto()));
        if (communityUser.getId().equals(discussionTopicDoc.getUserRef().getId())) {
            userActivity.delete(new ObjectId(discussionTopicDto.getUserRefDto().getId()), ActionType.post, new PostRef(discussionTopicDoc.getId(), null, PostType.discussion, PostMedium.text, null));
            activityFeed.setField(discussionTopicDoc.getId(), FeedDocField.userRef, communityUser);
            return discussionTopicDtoDocMapper.toDto(discussionTopicDoc);
        }
        return null;
    }
}
