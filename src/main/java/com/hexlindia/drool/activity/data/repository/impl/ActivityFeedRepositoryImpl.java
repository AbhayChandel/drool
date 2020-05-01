package com.hexlindia.drool.activity.data.repository.impl;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
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

    private static final String FIELD_ID = "_id";

    @Override
    public List<FeedDoc> getFeed(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("datePosted").descending());
        return mongoOperations.find(new Query().with(pageable), FeedDoc.class);
    }

    @Override
    public DeleteResult delete(ObjectId id) {
        return mongoOperations.remove(Query.query(new Criteria(FIELD_ID).is(id)), FeedDoc.class);
    }

    @Override
    public FeedDoc save(FeedDoc feedDoc) {
        return this.mongoOperations.save(feedDoc);
    }

    @Override
    public FeedDoc setField(ObjectId id, FeedDocField feedDocField, Object value) {
        return mongoOperations.findAndModify(new Query(where(FIELD_ID).is(id)), new Update().set(feedDocField.toString(), value), FindAndModifyOptions.options().returnNew(true), FeedDoc.class);
    }

    @Override
    public FeedDoc incrementDecrementField(ObjectId id, FeedDocField feedDocField, int value) {
        return mongoOperations.findAndModify(new Query(where(FIELD_ID).is(id)), new Update().inc(feedDocField.toString(), value), FindAndModifyOptions.options().returnNew(true), FeedDoc.class);
    }


}
