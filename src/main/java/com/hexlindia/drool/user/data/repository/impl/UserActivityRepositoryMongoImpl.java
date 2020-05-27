package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepositoryMongo;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserActivityRepositoryMongoImpl implements UserActivityRepositoryMongo {

    private MongoOperations mongoOperations;
    private PostRefMapper postRefMapper;

    public UserActivityRepositoryMongoImpl(MongoOperations mongoOperations, PostRefMapper postRefMapper) {
        this.mongoOperations = mongoOperations;
        this.postRefMapper = postRefMapper;
    }

    private static final String ID = "id";
    private static final String _ID = "_id";
    private static final String DOT = ".";
    private static final String FIELD_POSTS = "posts";
    private static final String FIELD_VIDEOS = "videos";
    private static final String FIELD_GUIDES = "guides";
    private static final String FIELD_REVIEWS = "reviews";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_DISCUSSIONS = "discussions";
    private static final String FIELD_LIKES = "likes";
    private static final String FIELD_COMMENTS = "comments";
    private static final String FIELD_REPLIES = "replies";
    private static final String FIELD_VIOLATIONS = "violations";
    private static final String FIELD_REPORTED_VIOLATIONS = "reported_violations";


    @Override
    public UpdateResult add(ObjectId userId, ActionType actionType, PostRef postRef) {
        return mongoOperations.upsert(getUser(userId), new Update().addToSet(getActionField(actionType) + DOT + getFieldName(postRef.getType(), postRef.getMedium()), postRef), UserActivityDoc.class);
    }

    @Override
    public UpdateResult delete(ObjectId userId, ActionType actionType, PostRef postRef) {
        Query queryPost = Query.query(Criteria.where(_ID).is(postRef.getId()));
        Update update = new Update().pull(getActionField(actionType) + DOT + getFieldName(postRef.getType(), postRef.getMedium()), queryPost);
        return mongoOperations.updateFirst(getUser(userId), update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult update(ObjectId userId, ActionType actionType, PostRef postRef) {
        String post = getActionField(actionType) + DOT + getFieldName(postRef.getType(), postRef.getMedium());
        Query queryPost = Query.query(Criteria.where(ID).is(userId).andOperator(Criteria.where(post + DOT + _ID).is(postRef.getId())));
        Update update = new Update().set(post + ".$.title", postRef.getTitle());
        return mongoOperations.updateFirst(queryPost, update, UserActivityDoc.class);
    }

    private Query getUser(ObjectId userId) {
        return Query.query(Criteria.where(ID).is(userId));
    }

    private String getActionField(ActionType actionType) {
        switch (actionType) {
            case post:
                return FIELD_POSTS;
            case like:
                return FIELD_LIKES;
            default:
                return "";
        }
    }

    private String getFieldName(PostType postType, PostFormat postFormat) {
        switch (postType) {
            case guide:
                switch (postFormat) {
                    case video:
                        return FIELD_GUIDES + DOT + FIELD_VIDEOS;
                    default:
                        return "";
                }
            case review:
                switch (postFormat) {
                    case video:
                        return FIELD_REVIEWS + DOT + FIELD_VIDEOS;
                    case article:
                        return FIELD_REVIEWS + DOT + FIELD_TEXT;
                    default:
                        return "";
                }
            case discussion:
                return FIELD_DISCUSSIONS;
            case reply:
                return FIELD_REPLIES;
            case comment:
                return FIELD_COMMENTS;
            default:
                return "";
        }
    }

    @Override
    public UpdateResult addViolation(ObjectId userId, ViolationReportRef violationReportRef) {
        return mongoOperations.upsert(query(where(ID).is(userId)), new Update().addToSet(FIELD_VIOLATIONS, violationReportRef), UserActivityDoc.class);
    }

    @Override
    public UpdateResult addReportedViolation(ObjectId userId, ViolationReportRef violationReportRef) {
        return mongoOperations.upsert(query(where(ID).is(userId)), new Update().addToSet(FIELD_REPORTED_VIOLATIONS, violationReportRef), UserActivityDoc.class);
    }


}
