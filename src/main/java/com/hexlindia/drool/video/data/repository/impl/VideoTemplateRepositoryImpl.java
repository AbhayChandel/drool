package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
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
        videoDoc = this.mongoOperations.insert(videoDoc);
        userActivityRepository.addVideo(videoDoc);
        return videoDoc;
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
    public VideoComment insertComment(PostRef postRef, VideoComment videoComment) {
        videoComment.setDatePosted(LocalDateTime.now());
        UpdateResult commentInsertResult = mongoOperations.updateFirst(new Query(where("id").is(postRef.getId())), new Update().addToSet("commentList", videoComment), VideoDoc.class);
        UpdateResult userActivityResult = userActivityRepository.addVideoComment(videoComment.getUserRef().getId(), new CommentRef(videoComment.getId(), videoComment.getComment(), postRef, videoComment.getDatePosted()));
        log.debug("Insert comment modify count: {}. Insert to user activity modify count: {}", commentInsertResult.getModifiedCount(), userActivityResult.getModifiedCount());
        if (commentInsertResult.getModifiedCount() > 0 && ((userActivityResult.getMatchedCount() > 0 && userActivityResult.getModifiedCount() > 0) || userActivityResult.getUpsertedId() != null)) {
            return videoComment;
        }
        return null;
    }

    @Override
    public boolean deleteComment(VideoCommentDto videoCommentDto) {
        Query queryVideo = Query.query(Criteria.where("_id").is(videoCommentDto.getPostRefDto().getId()));
        Query queryComment = Query.query(Criteria.where("_id").is(videoCommentDto.getId()));
        Update update = new Update().pull("commentList", queryComment);
        UpdateResult commentDeleteResult = mongoOperations.updateFirst(queryVideo, update, VideoDoc.class);
        return commentDeleteResult.getModifiedCount() > 0;
    }
}
