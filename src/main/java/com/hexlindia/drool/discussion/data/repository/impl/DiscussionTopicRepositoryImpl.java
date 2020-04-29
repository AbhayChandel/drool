package com.hexlindia.drool.discussion.data.repository.impl;

import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class DiscussionTopicRepositoryImpl implements DiscussionTopicRepository {

    private final MongoOperations mongoOperations;

    @Override
    public DiscussionTopicDoc save(DiscussionTopicDoc discussionTopicDoc) {
        return mongoOperations.save(discussionTopicDoc);
    }

    @Override
    public Optional<DiscussionTopicDoc> findById(ObjectId id) {
        MatchOperation match = match(new Criteria("_id").is(id).andOperator(new Criteria("active").is(true)));
        ProjectionOperation project = Aggregation.project("title", "userRef", "datePosted", "dateLastActive", "views", "likes", "replies").
                        and(ArrayOperators.arrayOf(ConditionalOperators.ifNull("replies").then(Collections.emptyList())).length()).as("repliesCount");


        AggregationResults<DiscussionTopicDoc> results = this.mongoOperations.aggregate(Aggregation.newAggregation(
                match,
                project
        ), "discussions", DiscussionTopicDoc.class);
        DiscussionTopicDoc discussionTopicDoc = results.getUniqueMappedResult();
        return discussionTopicDoc == null ? Optional.empty() : Optional.of(discussionTopicDoc);
    }

    @Override
    public boolean updateTopicTitle(String title, ObjectId discussionId) {
        return mongoOperations.updateFirst(new Query(where("id").is(discussionId)), new Update().set("title", title), DiscussionTopicDoc.class).getModifiedCount() > 0;
    }

    @Override
    public DiscussionTopicDoc incrementViews(ObjectId discussionId) {
        return mongoOperations.findAndModify(new Query(where("id").is(discussionId)), new Update().inc("views", 1), FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
    }

    @Override
    public DiscussionTopicDoc incrementLikes(ObjectId discussionId) {
        return mongoOperations.findAndModify(new Query(where("id").is(discussionId)), new Update().inc("likes", 1), FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
    }

    @Override
    public DiscussionTopicDoc decrementLikes(ObjectId discussionId) {
        return mongoOperations.findAndModify(new Query(where("id").is(discussionId)), new Update().inc("likes", -1), FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
    }

    @Override
    public DiscussionTopicDoc updateUser(ObjectId discussionId, UserRef newUserRef, UserRef oldUserRef) {
        return mongoOperations.findAndModify(new Query(where("id").is(discussionId)), new Update().set("userRef", newUserRef).set("oldUserRef", oldUserRef), FindAndModifyOptions.options().returnNew(true), DiscussionTopicDoc.class);
    }
}
