package com.hexlindia.drool.activity.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.activity.dto.mapper.DiscussionToFeedDocMapper;
import com.hexlindia.drool.activity.dto.mapper.FeedDocDtoMapper;
import com.hexlindia.drool.activity.dto.mapper.VideoToFeedDocMapper;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActivityFeedImpl implements ActivityFeed {

    private final VideoToFeedDocMapper videoToFeedDocMapper;
    private final ActivityFeedRepository activityFeedRepository;
    private final FeedDocDtoMapper feedDocDtoMapper;
    private final DiscussionToFeedDocMapper discussionToFeedDocMapper;

    @Override
    public List<FeedDto> getFeed(int page) {
        return feedDocDtoMapper.toDtoList(activityFeedRepository.getFeed(page));
    }

    @Override
    public void addVideo(VideoDoc videoDoc) {
        addToFeed(videoToFeedDocMapper.toFeedDoc(videoDoc));
    }

    @Override
    public void addDiscussion(DiscussionTopicDoc discussionTopicDoc) {
        addToFeed(discussionToFeedDocMapper.toFeedDoc(discussionTopicDoc));
    }

    private void addToFeed(FeedDoc feedDoc) {
        activityFeedRepository.save(feedDoc);
    }

    @Override
    public void setField(ObjectId postId, FeedDocFields feedDocFields, String value) {
        activityFeedRepository.setField(postId, feedDocFields, value);
    }

    @Override
    public void incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value) {
        activityFeedRepository.incrementDecrementField(postId, feedDocFields, value);
    }


}
