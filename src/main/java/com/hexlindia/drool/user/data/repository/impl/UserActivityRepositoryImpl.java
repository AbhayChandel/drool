package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.data.doc.ReplyRef;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.doc.VideoLike;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
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
public class UserActivityRepositoryImpl implements UserActivityRepository {

    private MongoOperations mongoOperations;
    private PostRefMapper postRefMapper;

    public UserActivityRepositoryImpl(MongoOperations mongoOperations, PostRefMapper postRefMapper) {
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
    private static final String FIELD_TEXT_REVIEWS = "textReviews";
    private static final String FIELD_DISCUSSIONS = "discussions";
    private static final String FIELD_LIKES = "likes";
    private static final String FIELD_COMMENTS = "comments";
    private static final String FIELD_REPLIES = "replies";
    private static final String FIELD_VIDEO_COMMENTS = "video_comments";
    private static final String FIELD_DISCUSSION_REPLIES = "discussion_replies";


    @Override
    public UpdateResult addVideo(VideoDoc videoDoc) {
        return mongoOperations.upsert(query(where(ID).is(videoDoc.getUserRef().getId())), new Update().addToSet(FIELD_POSTS + DOT + FIELD_VIDEOS + DOT + (videoDoc.getType().equalsIgnoreCase("guide") ? FIELD_GUIDES : FIELD_REVIEWS), new PostRef(videoDoc.getId(), videoDoc.getTitle(), null, null, videoDoc.getDatePosted())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return mongoOperations.upsert(query(where(ID).is(videoLikeUnlikeDto.getUserId())), new Update().addToSet(FIELD_LIKES + DOT + FIELD_VIDEOS, new VideoLike(new ObjectId(videoLikeUnlikeDto.getVideoId()), videoLikeUnlikeDto.getVideoTitle())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        Query queryUser = Query.query(Criteria.where(ID).is(new ObjectId(videoLikeUnlikeDto.getUserId())));
        Query queryVideo = Query.query(Criteria.where("videoId").is(new ObjectId(videoLikeUnlikeDto.getVideoId())));
        Update update = new Update().pull(FIELD_LIKES + DOT + FIELD_VIDEOS, queryVideo);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addVideoComment(ObjectId userId, CommentRef commentRef) {
        return mongoOperations.upsert(query(where(ID).is(userId)), new Update().addToSet(FIELD_VIDEO_COMMENTS, commentRef), UserActivityDoc.class);
    }

    @Override
    public UpdateResult updateVideoComment(ObjectId userId, CommentRef commentRef) {
        Query query = Query.query(Criteria.where(ID).is(userId).andOperator(Criteria.where(FIELD_VIDEO_COMMENTS + DOT + _ID).is(commentRef.getId())));
        Update update = new Update().set(FIELD_VIDEO_COMMENTS + ".$.comment", commentRef.getComment());
        return mongoOperations.updateFirst(query, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteVideoComment(VideoCommentDto videoCommentDto) {
        Query queryUser = Query.query(Criteria.where(ID).is(new ObjectId(videoCommentDto.getUserRefDto().getId())));
        Query queryComment = Query.query(Criteria.where(_ID).is(videoCommentDto.getId()));
        Update update = new Update().pull(FIELD_VIDEO_COMMENTS, queryComment);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addCommentLike(VideoCommentDto videoCommentDto) {
        PostRef postRef = postRefMapper.toDoc(videoCommentDto.getPostRefDto());
        Update update = new Update().addToSet(FIELD_LIKES + DOT + FIELD_COMMENTS, new CommentRef(new ObjectId(videoCommentDto.getId()), videoCommentDto.getComment(), postRef, null));
        return mongoOperations.upsert(query(where(ID).is(videoCommentDto.getUserRefDto().getId())), update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteCommentLike(VideoCommentDto videoCommentDto) {
        Query queryUser = Query.query(Criteria.where(ID).is(new ObjectId(videoCommentDto.getUserRefDto().getId())));
        Query queryComment = Query.query(Criteria.where(_ID).is(videoCommentDto.getId()));
        Update update = new Update().pull(FIELD_LIKES + DOT + FIELD_COMMENTS, queryComment);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addTextReview(ReviewDoc reviewDoc) {
        return mongoOperations.upsert(query(where(ID).is(reviewDoc.getUserRef().getId())), new Update().addToSet(FIELD_POSTS + DOT + FIELD_TEXT_REVIEWS, new PostRef(reviewDoc.getId(), reviewDoc.getReviewSummary(), null, null, reviewDoc.getDatePosted())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult addDiscussion(DiscussionTopicDoc discussionTopicDoc) {
        return mongoOperations.upsert(query(where(ID).is(discussionTopicDoc.getUserRef().getId())), new Update().addToSet(FIELD_POSTS + DOT + FIELD_DISCUSSIONS, new PostRef(discussionTopicDoc.getId(), discussionTopicDoc.getTitle(), null, null, discussionTopicDoc.getDatePosted())), UserActivityDoc.class);
    }

    @Override
    public UpdateResult addDiscussionReply(ObjectId userId, ReplyRef replyRef) {
        return mongoOperations.upsert(query(where(ID).is(userId)), new Update().addToSet(FIELD_DISCUSSION_REPLIES, replyRef), UserActivityDoc.class);
    }

    @Override
    public UpdateResult updateDiscussionReply(ObjectId userId, ReplyRef replyRef) {
        Query query = Query.query(Criteria.where(ID).is(userId).andOperator(Criteria.where(FIELD_DISCUSSION_REPLIES + DOT + _ID).is(replyRef.getId())));
        Update update = new Update().set(FIELD_DISCUSSION_REPLIES + ".$.reply", replyRef.getReply());
        return mongoOperations.updateFirst(query, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteDiscussionReply(ObjectId userId, ObjectId replyId) {
        Query queryUser = Query.query(Criteria.where(ID).is(userId));
        Query queryReply = Query.query(Criteria.where(_ID).is(replyId));
        Update update = new Update().pull(FIELD_DISCUSSION_REPLIES, queryReply);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult addDiscussionReplyLike(ObjectId userId, ReplyRef replyRef) {
        Update update = new Update().addToSet(FIELD_LIKES + DOT + FIELD_REPLIES, replyRef);
        return mongoOperations.upsert(query(where(ID).is(userId)), update, UserActivityDoc.class);
    }

    @Override
    public UpdateResult deleteDiscussionReplyLike(ObjectId userId, ObjectId replyId) {
        Query queryUser = Query.query(Criteria.where(ID).is(userId));
        Query queryComment = Query.query(Criteria.where(_ID).is(replyId));
        Update update = new Update().pull(FIELD_LIKES + DOT + FIELD_REPLIES, queryComment);
        return mongoOperations.updateFirst(queryUser, update, UserActivityDoc.class);
    }
}
