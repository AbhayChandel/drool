package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDataAggregation;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
public class VideoTemplateRepositoryImpl implements VideoTemplateRepository {

    private static final String VIDEO_COLLECTION_NAME = "videos";

    private final MongoOperations mongoOperations;
    private final UserActivityRepository userActivityRepository;

    private static final String COMMENT_LIST = "commentList";

    @Autowired
    public VideoTemplateRepositoryImpl(MongoOperations mongoOperations, UserActivityRepository userActivityRepository) {
        this.mongoOperations = mongoOperations;
        this.userActivityRepository = userActivityRepository;
    }

    @Override
    public VideoDoc save(VideoDoc videoDoc) {
        return this.mongoOperations.save(videoDoc);
    }

    @Override
    public VideoDoc findByIdAndActiveTrue(String id) {
        return mongoOperations.findOne(query(where("id").is(id).andOperator(where("active").is(true))), VideoDoc.class);
    }

    @Override
    public VideoThumbnailDataAggregation getLatestThreeVideosByUser(String userId) {

        MatchOperation matchUserVideos = match(new Criteria("userRef._id").is(userId).andOperator(new Criteria("active").is(true)));
        FacetOperation facet = facet(sort(Sort.Direction.DESC, "datePosted"), limit(3)).as("videoThumbnailList")
                .and(count().as("totalVideoCount")).as("count");
        ProjectionOperation project = project("videoThumbnailList", "count.totalVideoCount");

        AggregationResults<VideoThumbnailDataAggregation> results = this.mongoOperations.aggregate(Aggregation.newAggregation(
                matchUserVideos,
                facet,
                project
        ), VIDEO_COLLECTION_NAME, VideoThumbnailDataAggregation.class);

        return results.getUniqueMappedResult();
    }

    @Override
    public boolean updateReviewId(ObjectId videoId, ObjectId reviewId) {
        UpdateResult updateResult = mongoOperations.updateFirst(new Query(where("id").is(videoId)), new Update().set("reviewId", reviewId), VideoDoc.class);
        if (updateResult.getModifiedCount() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        VideoDoc videoDoc = mongoOperations.findAndModify(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())), new Update().inc("likes", 1), FindAndModifyOptions.options().returnNew(true), VideoDoc.class);
        return MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes());
    }

    @Override
    public String deleteVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        VideoDoc videoDoc = mongoOperations.findAndModify(new Query(where("id").is(videoLikeUnlikeDto.getVideoId())), new Update().inc("likes", -1), FindAndModifyOptions.options().returnNew(true), VideoDoc.class);
        return MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes());
    }

    @Override
    public VideoComment insertComment(PostRef postRef, VideoComment videoComment) {
        videoComment.setDatePosted(LocalDateTime.now());
        UpdateResult commentInsertResult = mongoOperations.updateFirst(new Query(where("id").is(postRef.getId())), new Update().addToSet(COMMENT_LIST, videoComment), VideoDoc.class);
        if (commentInsertResult.getModifiedCount() > 0) {
            return videoComment;
        }
        return null;
    }

    @Override
    public boolean deleteComment(VideoCommentDto videoCommentDto) {
        Query queryVideo = Query.query(Criteria.where("_id").is(videoCommentDto.getPostRefDto().getId()));
        Query queryComment = Query.query(Criteria.where("_id").is(videoCommentDto.getId()));
        Update update = new Update().pull(COMMENT_LIST, queryComment);
        UpdateResult commentDeleteResult = mongoOperations.updateFirst(queryVideo, update, VideoDoc.class);
        return (commentDeleteResult.getModifiedCount() > 0);
    }

    @Override
    public String saveCommentLike(VideoCommentDto videoCommentDto) {
        Query query = Query.query(Criteria.where("_id").is(videoCommentDto.getPostRefDto().getId()).andOperator(Criteria.where(COMMENT_LIST).elemMatch(Criteria.where("_id").is(videoCommentDto.getId()))));
        Update update = new Update().inc("commentList.$.likes", 1);
        UpdateResult commentLikeResult = mongoOperations.updateFirst(query, update, VideoDoc.class);
        return (commentLikeResult.getMatchedCount() > 0 && commentLikeResult.getModifiedCount() > 0) ? Integer.toString(Integer.valueOf(videoCommentDto.getLikes()) + 1) : videoCommentDto.getLikes();
    }

    @Override
    public String deleteCommentLike(VideoCommentDto videoCommentDto) {
        Query query = Query.query(Criteria.where("_id").is(videoCommentDto.getPostRefDto().getId()).andOperator(Criteria.where(COMMENT_LIST).elemMatch(Criteria.where("_id").is(videoCommentDto.getId()))));
        Update update = new Update().inc("commentList.$.likes", -1);
        UpdateResult commentLikeResult = mongoOperations.updateFirst(query, update, VideoDoc.class);
        return (commentLikeResult.getMatchedCount() > 0 && commentLikeResult.getModifiedCount() > 0) ? Integer.toString(Integer.valueOf(videoCommentDto.getLikes()) - 1) : videoCommentDto.getLikes();
    }
}
