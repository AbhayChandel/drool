package com.hexlindia.drool.activity.dto.mapper;

import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiscussionToFeedDocMapperTest {

    @Autowired
    DiscussionToFeedDocMapper discussionToFeedDocMapper;

    @Test
    void toFeedDoc() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        ObjectId discussiionId = ObjectId.get();
        discussionTopicDoc.setId(discussiionId);
        discussionTopicDoc.setTitle("This is a DiscussionToFeedDocMapperTest title");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionTopicDoc.setViews(5230);
        discussionTopicDoc.setLikes(1234);
        discussionTopicDoc.setRepliesCount(200);
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);

        FeedDoc feedDoc = discussionToFeedDocMapper.toFeedDoc(discussionTopicDoc);
        assertEquals(discussiionId, feedDoc.getPostId());
        assertEquals("discussion", feedDoc.getPostType());
        assertEquals("text", feedDoc.getPostMedium());
        assertEquals("This is a DiscussionToFeedDocMapperTest title", feedDoc.getTitle());
        assertEquals(datePosted, feedDoc.getDatePosted());
        assertEquals(1234, feedDoc.getLikes());
        assertEquals(5230, feedDoc.getViews());
        assertEquals(200, feedDoc.getComments());
        assertEquals(userId, feedDoc.getUserRef().getId());
        assertEquals("shabana", feedDoc.getUserRef().getUsername());
    }
}