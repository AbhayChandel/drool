package com.hexlindia.drool.activity.data.doc;

import com.hexlindia.drool.activity.dto.mapper.VideoToFeedDocMapper;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VideoToFeedDocMapperTest {

    @Autowired
    private VideoToFeedDocMapper videoToFeedDocMapper;

    @Test
    void testToFeedDoc() {
        ObjectId userId = new ObjectId();
        VideoDoc videoDoc = new VideoDoc(PostType.review, "This video is going to activity feed", "This is a description for test video", "aanx323faid",
                Arrays.asList(new ProductRef("1", "Lakme 9to5", "lipstick"), new ProductRef("2", "Maybelline Collosal Kajal", "kajal")),
                new UserRef(userId, "shabanastyle"));
        ObjectId id = new ObjectId();
        videoDoc.setId(id);
        LocalDateTime datePosted = LocalDateTime.now();
        videoDoc.setDatePosted(datePosted);

        FeedDoc feedDoc = videoToFeedDocMapper.toFeedDoc(videoDoc);
        assertEquals(id, feedDoc.getId());
        assertEquals("review", feedDoc.getPostType());
        assertEquals("video", feedDoc.getPostMedium());
        assertEquals("This video is going to activity feed", feedDoc.getTitle());
        assertEquals("aanx323faid", feedDoc.getSourceId());
        assertEquals(datePosted, feedDoc.getDatePosted());
        assertEquals(0, feedDoc.getLikes());
        assertEquals(0, feedDoc.getViews());
        assertEquals(0, feedDoc.getComments());
        assertEquals(2, feedDoc.getProductRefList().size());
        assertEquals("1", feedDoc.getProductRefList().get(0).getId());
        assertEquals("Lakme 9to5", feedDoc.getProductRefList().get(0).getName());
        assertEquals("lipstick", feedDoc.getProductRefList().get(0).getType());
        assertEquals("2", feedDoc.getProductRefList().get(1).getId());
        assertEquals("Maybelline Collosal Kajal", feedDoc.getProductRefList().get(1).getName());
        assertEquals("kajal", feedDoc.getProductRefList().get(1).getType());
        assertEquals(userId, feedDoc.getUserRef().getId());
        assertEquals("shabanastyle", feedDoc.getUserRef().getUsername());
    }

}