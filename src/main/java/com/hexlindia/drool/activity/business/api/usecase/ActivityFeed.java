package com.hexlindia.drool.activity.business.api.usecase;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActivityFeed {

    void addVideo(VideoDoc videoDoc);

    void setField(ObjectId postId, FeedDocField feedDocField, Object value);

    void incrementDecrementField(ObjectId postId, FeedDocField feedDocField, int value);

    List<FeedDto> getFeed(int page);

    void addDiscussion(DiscussionTopicDoc discussionTopicDoc);
}
