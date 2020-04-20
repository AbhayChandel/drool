package com.hexlindia.drool.activity.business.api.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActivityFeed {

    void addVideo(VideoDoc videoDoc);

    void setField(ObjectId postId, FeedDocFields feedDocFields, String value);

    void incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value);

    List<FeedDto> getFeed(int page);

    void addDiscussion(DiscussionTopicDoc discussionTopicDoc);
}
