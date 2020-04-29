package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBTestConfig;
import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.doc.UserActivityDoc;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(MongoDBTestConfig.class)
class UserActivityRepositoryImplTest {

    private final UserActivityRepository userActivityRepository;
    private final MongoOperations mongoOperations;


    @Autowired
    public UserActivityRepositoryImplTest(UserActivityRepository userActivityRepository, MongoTemplate mongoOperations) {
        this.userActivityRepository = userActivityRepository;
        this.mongoOperations = mongoOperations;
    }

    private ObjectId userId = ObjectId.get();
    private PostRef videoGuide;
    private PostRef videoReview;
    private PostRef textReview;
    private PostRef discussion;
    private PostRef discussionReply;
    private PostRef videoComment;

    @BeforeEach
    void setUp() {

        videoGuide = new PostRef(ObjectId.get(), "This is a test video guide", PostType.guide, PostMedium.video, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.guides.videos", videoGuide), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.guides.videos", videoGuide), UserActivityDoc.class);

        videoReview = new PostRef(ObjectId.get(), "This is a test video review", PostType.review, PostMedium.video, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.reviews.videos", videoReview), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.reviews.videos", videoReview), UserActivityDoc.class);

        textReview = new PostRef(ObjectId.get(), "This is a test text review", PostType.review, PostMedium.text, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.reviews.text", textReview), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.reviews.text", textReview), UserActivityDoc.class);

        discussion = new PostRef(ObjectId.get(), "This is a test discussion topic", PostType.discussion, PostMedium.text, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.discussions", discussion), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.discussions", discussion), UserActivityDoc.class);

        discussionReply = new PostRef(ObjectId.get(), "This is a test discussion reply", PostType.reply, PostMedium.text, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.replies", discussionReply), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.replies", discussionReply), UserActivityDoc.class);

        videoComment = new PostRef(ObjectId.get(), "This is a test video comment", PostType.comment, PostMedium.text, null);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("posts.comments", videoComment), UserActivityDoc.class);
        mongoOperations.upsert(getUser(userId), new Update().addToSet("likes.comments", videoComment), UserActivityDoc.class);

        violationReportRef = new ViolationReportRef();
        violationReportRef.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        violationReportRef.setPost(discussionReply);
        violationReportRef.setMainPost(discussion);
        violationReportRef.setDateReported(LocalDateTime.now());
    }

    private Query getUser(ObjectId userId) {
        return Query.query(Criteria.where("id").is(userId));
    }

    @Test
    void add() {

        videoGuide = new PostRef(ObjectId.get(), "This is a test video guide", PostType.guide, PostMedium.video, null);
        UpdateResult result = userActivityRepository.add(userId, ActionType.post, videoGuide);
        assertTrue(result.getModifiedCount() > 0);

        videoReview = new PostRef(ObjectId.get(), "This is a test video review", PostType.review, PostMedium.video, null);
        assertTrue(userActivityRepository.add(userId, ActionType.post, videoReview).getModifiedCount() > 0);

        textReview = new PostRef(ObjectId.get(), "This is a test text review", PostType.review, PostMedium.text, null);
        assertTrue(userActivityRepository.add(userId, ActionType.post, textReview).getModifiedCount() > 0);

        discussion = new PostRef(ObjectId.get(), "This is a test discussion topic", PostType.discussion, PostMedium.text, null);
        assertTrue(userActivityRepository.add(userId, ActionType.post, discussion).getModifiedCount() > 0);

        discussionReply = new PostRef(ObjectId.get(), "This is a test discussion reply", PostType.reply, PostMedium.text, null);
        assertTrue(userActivityRepository.add(userId, ActionType.post, discussionReply).getModifiedCount() > 0);

        videoComment = new PostRef(ObjectId.get(), "This is a test video comment", PostType.comment, PostMedium.text, null);
        assertTrue(userActivityRepository.add(userId, ActionType.post, videoComment).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, videoGuide).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, videoReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, textReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, discussion).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, discussionReply).getModifiedCount() > 0);

        assertTrue(userActivityRepository.add(userId, ActionType.like, videoComment).getModifiedCount() > 0);
    }

    @Test
    void delete() {
        assertTrue(userActivityRepository.delete(userId, ActionType.post, videoGuide).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.post, videoReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.post, textReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.post, discussion).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.post, discussionReply).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.post, videoComment).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, videoGuide).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, videoReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, textReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, discussion).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, discussionReply).getModifiedCount() > 0);

        assertTrue(userActivityRepository.delete(userId, ActionType.like, videoComment).getModifiedCount() > 0);
    }

    @Test
    void update() {
        videoGuide.setTitle("updated");
        UpdateResult result = userActivityRepository.update(userId, ActionType.post, videoGuide);
        assertTrue(result.getModifiedCount() > 0);

        videoReview.setTitle("updated");
        assertTrue(userActivityRepository.update(userId, ActionType.post, videoReview).getModifiedCount() > 0);

        textReview.setTitle("updated");
        assertTrue(userActivityRepository.update(userId, ActionType.post, textReview).getModifiedCount() > 0);

        discussion.setTitle("updated");
        assertTrue(userActivityRepository.update(userId, ActionType.post, discussion).getModifiedCount() > 0);

        discussionReply.setTitle("updated");
        assertTrue(userActivityRepository.update(userId, ActionType.post, discussionReply).getModifiedCount() > 0);

        videoComment.setTitle("updated");
        assertTrue(userActivityRepository.update(userId, ActionType.post, videoComment).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, videoGuide).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, videoReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, textReview).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, discussion).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, discussionReply).getModifiedCount() > 0);

        assertTrue(userActivityRepository.update(userId, ActionType.like, videoComment).getModifiedCount() > 0);
    }

    ViolationReportRef violationReportRef = null;

    @Test
    void addViolation() {
        assertTrue(userActivityRepository.addViolation(userId, violationReportRef).getModifiedCount() > 0);
    }

    @Test
    void addReportedViolation() {
        assertTrue(userActivityRepository.addReportedViolation(ObjectId.get(), violationReportRef).getUpsertedId() != null);
    }
}