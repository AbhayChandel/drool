package com.hexlindia.drool.discussion.data.repository.api;

import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import org.bson.types.ObjectId;

public interface DiscussionReplyRepository {

    public boolean saveReply(DiscussionReplyDoc discussionReplyDoc, ObjectId discussionId);

    public boolean updateReply(String reply, ObjectId replyId, ObjectId discussionId);

    public Integer incrementLikes(ObjectId replyId, ObjectId discussionId);

    public Integer decrementLikes(ObjectId replyId, ObjectId discussionId);

    public boolean setStatus(Boolean status, ObjectId replyId, ObjectId discussionId);

}
