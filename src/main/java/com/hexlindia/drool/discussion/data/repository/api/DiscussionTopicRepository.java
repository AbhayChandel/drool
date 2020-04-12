package com.hexlindia.drool.discussion.data.repository.api;

import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import org.bson.types.ObjectId;

public interface DiscussionTopicRepository {

    public DiscussionTopicDoc save(DiscussionTopicDoc discussionTopicDoc);

    public DiscussionTopicDoc findById(ObjectId id);

    public boolean updateTopicTitle(String title, ObjectId discussionId);

    public DiscussionTopicDoc incrementViews(ObjectId discussionId);

    public DiscussionTopicDoc incrementLikes(ObjectId discussionId);

    public DiscussionTopicDoc decrementLikes(ObjectId discussionId);
}
