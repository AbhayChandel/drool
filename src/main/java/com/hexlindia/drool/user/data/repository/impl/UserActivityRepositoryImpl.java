package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserActivityRepositoryImpl implements UserActivityRepository {

    private MongoOperations mongoOperations;

    public UserActivityRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public UpdateResult addVideoLike(String videoId, String videoTitle, String userId) {
        VideoLike videoLike = new VideoLike(videoId, videoTitle);
        Update update = new Update();
        update.addToSet("likes.videos", videoLike);
        return mongoOperations.upsert(query(where("userId").is(userId)), update, UserActivityDoc.class);
    }
}
