package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
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
    private PostRefMapper postRefMapper;

    public UserActivityRepositoryImpl(MongoOperations mongoOperations, PostRefMapper postRefMapper) {
        this.mongoOperations = mongoOperations;
        this.postRefMapper = postRefMapper;
    }


    private static final String USER_ID = "userId";

    @Override
    public UpdateResult addVideo(VideoDoc videoDoc) {
        String arrayPath = videoDoc.getType().equalsIgnoreCase("guide") ? "post.videos.guides" : "post.videos.reviews";
        return mongoOperations.upsert(query(where(USER_ID).is(videoDoc.getUserRef().getId())), new Update().addToSet(arrayPath, new PostRef(videoDoc.getId(), videoDoc.getTitle(), null, null, videoDoc.getDatePosted())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return mongoOperations.upsert(query(where(USER_ID).is(videoLikeUnlikeDto.getUserId())), new Update().addToSet("likes.videos", new VideoLike(videoLikeUnlikeDto.getVideoId(), videoLikeUnlikeDto.getVideoTitle())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult removeVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        Query queryUser = Query.query(Criteria.where(USER_ID).is(videoLikeUnlikeDto.getUserId()));
        Query queryVideo = Query.query(Criteria.where("videoId").is(videoLikeUnlikeDto.getVideoId()));
        Update update = new Update().pull("likes.videos", queryVideo);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addVideoComment(String userId, CommentRef commentRef) {
        return mongoOperations.upsert(query(where(USER_ID).is(userId)), new Update().addToSet("comments", commentRef), UserActivityDoc.class);
    }

    @Override
    public UpdateResult removeVideoComment(VideoCommentDto videoCommentDto) {
        Query queryUser = Query.query(Criteria.where(USER_ID).is(videoCommentDto.getUserRefDto().getId()));
        Query queryComment = Query.query(Criteria.where("_id").is(videoCommentDto.getId()));
        Update update = new Update().pull("comments", queryComment);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addCommentLike(VideoCommentDto videoCommentDto) {
        PostRef postRef = postRefMapper.toDoc(videoCommentDto.getPostRefDto());
        Update update = new Update().addToSet("likes.comments", new CommentRef(videoCommentDto.getId(), videoCommentDto.getComment(), postRef, null));
        return mongoOperations.upsert(query(where(USER_ID).is(videoCommentDto.getUserRefDto().getId())), update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteCommentLike(VideoCommentDto videoCommentDto) {
        Query queryUser = Query.query(Criteria.where(USER_ID).is(videoCommentDto.getUserRefDto().getId()));
        Query queryComment = Query.query(Criteria.where("_id").is(videoCommentDto.getId()));
        Update update = new Update().pull("likes.comments", queryComment);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }
}
