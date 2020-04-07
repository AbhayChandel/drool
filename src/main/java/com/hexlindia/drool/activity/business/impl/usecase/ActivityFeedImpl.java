package com.hexlindia.drool.activity.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.doc.VideoToFeedDocMapper;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActivityFeedImpl implements ActivityFeed {

    private final VideoToFeedDocMapper videoToFeedDocMapper;
    private final ActivityFeedRepository activityFeedRepository;

    @Override
    public void addVideo(VideoDoc videoDoc) {
        addToFeed(videoToFeedDocMapper.toFeedDoc(videoDoc));
    }

    @Override
    public void setField(ObjectId postId, FeedDocFields feedDocFields, String value) {
        activityFeedRepository.setField(postId, feedDocFields, value);
    }

    @Override
    public void incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value) {
        activityFeedRepository.incrementDecrementField(postId, feedDocFields, value);
    }

    private void addToFeed(FeedDoc feedDoc) {
        activityFeedRepository.save(feedDoc);
    }
}
