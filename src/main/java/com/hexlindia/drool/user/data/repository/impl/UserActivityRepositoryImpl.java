package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return mongoOperations.upsert(query(where("userId").is(videoLikeUnlikeDto.getUserId())), new Update().addToSet("likes.videos", new VideoLike(videoLikeUnlikeDto.getVideoId(), videoLikeUnlikeDto.getVideoTitle())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult removeVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        Query queryUser = Query.query(Criteria.where("userId").is(videoLikeUnlikeDto.getUserId()));
        Query queryVideo = Query.query(Criteria.where("videoId").is(videoLikeUnlikeDto.getVideoId()));
        Update update = new Update().pull("likes.videos", queryVideo);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }
}
