package com.hexlindia.drool.discussion.data.mongodb.impl;

import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.mongodb.api.DiscussionTopicMongoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class DiscussionTopicMongoRepositoryImpl implements DiscussionTopicMongoRepository {

    private final MongoOperations mongoOperations;

    @Override
    public DiscussionTopicDoc save(DiscussionTopicDoc discussionTopicDoc) {
        return mongoOperations.save(discussionTopicDoc);
    }

    @Override
    public DiscussionTopicDoc findById(ObjectId id) {
        MatchOperation match = match(new Criteria("_id").is(id).andOperator(new Criteria("active").is(true)));
        ProjectionOperation project = Aggregation.project("title", "userRef", "datePosted", "dateLastActive", "views", "likes", "replies").
                and(ArrayOperators.arrayOf("replies").length()).as("repliesCount");

        AggregationResults<DiscussionTopicDoc> results = this.mongoOperations.aggregate(Aggregation.newAggregation(
                match,
                project
        ), "discussions", DiscussionTopicDoc.class);
        return results.getUniqueMappedResult();
    }

    @Override
    public boolean updateTopicTitle(String title, ObjectId discussionId) {
        return mongoOperations.updateFirst(new Query(where("id").is(discussionId)), new Update().set("topic", title), DiscussionTopicDoc.class).getModifiedCount() > 0;
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
}
