package com.hexlindia.drool.activity.data.repository.impl;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ActivityFeedRepositoryImpl implements ActivityFeedRepository {

    private final MongoOperations mongoOperations;

    @Override
    public FeedDoc save(FeedDoc feedDoc) {
        return this.mongoOperations.save(feedDoc);
    }

    @Override
    public FeedDoc setField(ObjectId postId, FeedDocFields feedDocFields, String value) {
        return mongoOperations.findAndModify(new Query(where("postId").is(postId)), new Update().set(feedDocFields.toString(), value), FindAndModifyOptions.options().returnNew(true), FeedDoc.class);
    }

    @Override
    public FeedDoc incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value) {
        return mongoOperations.findAndModify(new Query(where("postId").is(postId)), new Update().inc(feedDocFields.toString(), value), FindAndModifyOptions.options().returnNew(true), FeedDoc.class);
    }

    @Override
    public List<FeedDoc> getFeed(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("datePosted").descending());
        return mongoOperations.find(new Query().with(pageable), FeedDoc.class);
    }
}
