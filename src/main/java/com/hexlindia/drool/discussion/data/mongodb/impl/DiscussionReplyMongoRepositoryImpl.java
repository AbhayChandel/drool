package com.hexlindia.drool.discussion.data.mongodb.impl;

import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.mongodb.api.DiscussionReplyMongoRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class DiscussionReplyMongoRepositoryImpl implements DiscussionReplyMongoRepository {

    private final MongoOperations mongoOperations;

    private static final String VIDEO_COLLECTION_NAME = "discussions";
    private static final String PROPERTY_REPLIES = "replies";

    @Override
    public boolean saveReply(DiscussionReplyDoc discussionReplyDoc, ObjectId discussionId) {
        UpdateResult result = mongoOperations.updateFirst(new Query(where("id").is(discussionId)), new Update().addToSet(PROPERTY_REPLIES, discussionReplyDoc).set("dateLastActive", LocalDateTime.now()), DiscussionTopicDoc.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean updateReply(String reply, ObjectId replyId, ObjectId discussionId) {
        Update update = new Update().set("replies.$.reply", reply);
        UpdateResult result = mongoOperations.updateFirst(findReply(replyId, discussionId), update, DiscussionTopicDoc.class);
        return result.getMatchedCount() > 0 && result.getModifiedCount() > 0;
    }

    @Override
    public Integer incrementLikes(ObjectId replyId, ObjectId discussionId) {
        Update update = new Update().inc("replies.$.likes", 1);
        DiscussionTopicDoc discussionTopicDoc = mongoOperations.findAndModify(findReply(replyId, discussionId), update, FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
        return retrieveReplyLikes(discussionTopicDoc, replyId);
    }

    private Integer retrieveReplyLikes(DiscussionTopicDoc discussionTopicDoc, ObjectId replyId) {
        if (discussionTopicDoc == null) {
            return 0;
        }
        for (DiscussionReplyDoc discussionReplyDoc : discussionTopicDoc.getDiscussionReplyDocList()) {
            if (discussionReplyDoc.getId().equals(replyId)) {
                return discussionReplyDoc.getLikes();
            }
        }
        return 0;
    }

    @Override
    public Integer decrementLikes(ObjectId replyId, ObjectId discussionId) {
        Update update = new Update().inc("replies.$.likes", -1);
        DiscussionTopicDoc discussionTopicDoc = mongoOperations.findAndModify(findReply(replyId, discussionId), update, FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
        return retrieveReplyLikes(discussionTopicDoc, replyId);
    }

    @Override
    public boolean setStatus(Boolean status, ObjectId replyId, ObjectId discussionId) {
        Update update = new Update().set("replies.$.active", status);
        UpdateResult result = mongoOperations.updateFirst(findReply(replyId, discussionId), update, DiscussionTopicDoc.class);
        return result.getMatchedCount() > 0 && result.getModifiedCount() > 0;
    }

    private Query findReply(ObjectId replyId, ObjectId discussionId) {
        return Query.query(Criteria.where("_id").is(discussionId).andOperator(Criteria.where(PROPERTY_REPLIES + "._id").is(replyId)));
    }
}
