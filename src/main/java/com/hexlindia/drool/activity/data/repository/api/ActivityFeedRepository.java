package com.hexlindia.drool.activity.data.repository.api;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActivityFeedRepository {

    FeedDoc save(FeedDoc feedDoc);

    FeedDoc setField(ObjectId postId, FeedDocField feedDocField, Object value);

    FeedDoc incrementDecrementField(ObjectId postId, FeedDocField feedDocField, int value);

    List<FeedDoc> getFeed(int page);
}
