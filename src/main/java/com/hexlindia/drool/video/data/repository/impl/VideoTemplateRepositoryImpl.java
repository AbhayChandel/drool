package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class VideoTemplateRepositoryImpl implements VideoTemplateRepository {

    private final MongoOperations mongoOperations;
    private final UserActivityRepository userActivityRepository;

    @Autowired
    public VideoTemplateRepositoryImpl(MongoOperations mongoOperations, UserActivityRepository userActivityRepository) {
        this.mongoOperations = mongoOperations;
        this.userActivityRepository = userActivityRepository;
    }

    @Override
    public VideoDoc insert(VideoDoc videoDoc) {
        return this.mongoOperations.insert(videoDoc);
    }

    @Override
    public VideoDoc findByIdAndActiveTrue(String id) {
        return mongoOperations.findOne(query(where("id").is(id).andOperator(where("active").is(true))), VideoDoc.class);
    }

    @Override
    public boolean incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        mongoOperations.updateFirst(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())),
                new Update().inc("likes", 1), VideoDoc.class);
        UpdateResult updateResult = userActivityRepository.addVideoLike(videoLikeUnlikeDto);
        return updateResult.getModifiedCount() != 0;
    }

    @Override
    public boolean decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        mongoOperations.updateFirst(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())),
                new Update().inc("likes", -1), VideoDoc.class);
        UpdateResult updateResult = userActivityRepository.removeVideoLike(videoLikeUnlikeDto);
        return updateResult.getModifiedCount() != 0;
    }
}
