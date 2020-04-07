package com.hexlindia.drool.activity.data.repository.impl;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ActivityFeedFeedRepositoryImplTest {

    @Autowired
    ActivityFeedRepository activityFeedRepository;

    @Autowired
    MongoOperations mongoOperations;

    ObjectId insertedPostId = ObjectId.get();

    @Test
    void testSave() {
        FeedDoc feedDoc = new FeedDoc();
        feedDoc.setPostId(ObjectId.get());
        feedDoc.setPostType("guide");
        feedDoc.setPostMedium("video");
        feedDoc.setTitle(("Test guide for repository testing"));
        feedDoc.setSourceId("ax4n6k5xk");
        feedDoc.setDatePosted(LocalDateTime.now());
        feedDoc.setLikes("0");
        feedDoc.setViews("0");
        feedDoc.setComments(0);
        feedDoc.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9to5", "lipstick"), new ProductRef("2", "Maybelline Collosal Kajal", "kajal")));
        feedDoc.setUserRef(new UserRef("123", "shabanastyle"));

        assertNotNull(activityFeedRepository.save(feedDoc));
    }

    @Test
    void testSetLikes() {
        assertEquals("201", activityFeedRepository.setField(insertedPostId, FeedDocFields.likes, "201").getLikes());
    }

    @Test
    void testSetViews() {
        assertEquals("301", activityFeedRepository.setField(insertedPostId, FeedDocFields.views, "301").getViews());
    }

    @Test
    void testIncrementComments() {
        assertEquals(401, activityFeedRepository.incrementDecrementField(insertedPostId, FeedDocFields.comments, 1).getComments());
    }

    @Test
    void testDecrementComments() {
        assertEquals(399, activityFeedRepository.incrementDecrementField(insertedPostId, FeedDocFields.comments, -1).getComments());
    }


    @BeforeEach
    void setup() {
        FeedDoc feedDoc = new FeedDoc();
        feedDoc.setPostId(insertedPostId);
        feedDoc.setPostType("guide");
        feedDoc.setPostMedium("video");
        feedDoc.setTitle(("Test guide for repository testing setup"));
        feedDoc.setSourceId("ax4n6k5xk");
        feedDoc.setDatePosted(LocalDateTime.now());
        feedDoc.setLikes("200");
        feedDoc.setViews("300");
        feedDoc.setComments(400);
        feedDoc.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9to5", "lipstick"), new ProductRef("2", "Maybelline Collosal Kajal", "kajal")));
        feedDoc.setUserRef(new UserRef("123", "shabanastyle"));
        this.mongoOperations.save(feedDoc);
    }
}