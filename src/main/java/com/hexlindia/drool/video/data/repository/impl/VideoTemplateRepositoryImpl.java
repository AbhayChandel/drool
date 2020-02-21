package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
    public String incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        VideoDoc videoDoc = mongoOperations.findAndModify(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())), new Update().inc("likes", 1), FindAndModifyOptions.options().returnNew(true), VideoDoc.class);
        userActivityRepository.addVideoLike(videoLikeUnlikeDto);
        return MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes());
    }

    @Override
    public String decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        VideoDoc videoDoc = mongoOperations.findAndModify(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())), new Update().inc("likes", -1), FindAndModifyOptions.options().returnNew(true), VideoDoc.class);
        UpdateResult updateResult = userActivityRepository.removeVideoLike(videoLikeUnlikeDto);
        return MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes());
    }

    @Override
    public boolean insertComment(PostRef postRef, VideoComment videoComment) {
        videoComment.setDatePosted(LocalDateTime.now());
        UpdateResult updateResult = mongoOperations.updateFirst(new Query(where("id").is(postRef.getId())), new Update().addToSet("videoCommentList", videoComment), VideoDoc.class);
        userActivityRepository.addVideoComment(videoComment.getUserRef().getId(), new CommentRef(videoComment.getId(), videoComment.getComment(), postRef, videoComment.getDatePosted()));
        return updateResult.getModifiedCount() > 0;
    }
}
