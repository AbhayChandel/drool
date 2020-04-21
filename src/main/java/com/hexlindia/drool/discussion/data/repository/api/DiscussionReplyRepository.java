package com.hexlindia.drool.discussion.data.repository.api;

import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import org.bson.types.ObjectId;

public interface DiscussionReplyRepository {

    boolean saveReply(DiscussionReplyDoc discussionReplyDoc, ObjectId discussionId);

    boolean updateReply(String reply, ObjectId replyId, ObjectId discussionId);

    Integer incrementLikes(ObjectId replyId, ObjectId discussionId);

    Integer decrementLikes(ObjectId replyId, ObjectId discussionId);

    boolean delete(ObjectId replyId, ObjectId discussionId);

}
