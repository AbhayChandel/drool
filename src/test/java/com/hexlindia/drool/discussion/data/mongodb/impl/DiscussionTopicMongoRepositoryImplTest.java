package com.hexlindia.drool.discussion.data.mongodb.impl;

import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.mongodb.api.DiscussionTopicMongoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(MongoDBConfig.class)
class DiscussionTopicMongoRepositoryImplTest {

    @Autowired
    private DiscussionTopicMongoRepository discussionTopicMongoRepository;

    private ObjectId insertDiscussionTopic;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setup() {
        insertDiscussionTopicDocs();
    }

    @Test
    void save() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setTitle("This topic is returned from db");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setLikes(190);
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        discussionTopicDoc = discussionTopicMongoRepository.save(discussionTopicDoc);
        assertNotNull(discussionTopicDoc.getId());

    }

    @Test
    void findById() {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicMongoRepository.findById(insertDiscussionTopic);
        assertNotNull(discussionTopicDoc);
    }

    @Test
    void updateTopicTitle() {
        assertTrue(discussionTopicMongoRepository.updateTopicTitle("This title was updated", insertDiscussionTopic));
    }

    @Test
    void incrementViews() {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicMongoRepository.incrementViews(insertDiscussionTopic);
        assertEquals(1191, discussionTopicDoc.getViews());
    }

    @Test
    void incrementLikes() {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicMongoRepository.incrementLikes(insertDiscussionTopic);
        assertEquals(501, discussionTopicDoc.getLikes());

    }

    @Test
    void decrementLikes() {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicMongoRepository.decrementLikes(insertDiscussionTopic);
        assertEquals(499, discussionTopicDoc.getLikes());
    }

    private void insertDiscussionTopicDocs() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setTitle("This a dummy discussion topic");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        discussionTopicDoc.setActive(true);
        discussionTopicDoc.setViews(1190);
        discussionTopicDoc.setLikes(500);
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setLikes(190);
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        mongoOperations.save(discussionTopicDoc);
        insertDiscussionTopic = discussionTopicDoc.getId();
    }
}