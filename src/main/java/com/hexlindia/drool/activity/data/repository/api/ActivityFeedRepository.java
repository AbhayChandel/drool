package com.hexlindia.drool.activity.data.repository.api;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import org.bson.types.ObjectId;

public interface ActivityFeedRepository {

    FeedDoc save(FeedDoc feedDoc);

    FeedDoc setField(ObjectId postId, FeedDocFields feedDocFields, String value);

    FeedDoc incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value);
}
