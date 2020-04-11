package com.hexlindia.drool.discussion.data.mongodb.impl;

import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.mongodb.api.DiscussionReplyMongoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(MongoDBConfig.class)
class DiscussionReplyMongoRepositoryImplTest {

    @Autowired
    DiscussionReplyMongoRepository discussionReplyMongoRepository;

    @Autowired
    MongoOperations mongoOperations;

    private ObjectId insertDiscussionTopic;
    private ObjectId insertedReplyId;

    @BeforeEach
    void setup() {
        insertDiscussionTopicDocs();
    }

    @Test
    void saveReply() {
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("This is a test reply.");
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setUserRef(new UserRef(ObjectId.get().toHexString(), "shabana"));
        discussionReplyDoc.setDatePosted(LocalDateTime.now());
        assertTrue(discussionReplyMongoRepository.saveReply(discussionReplyDoc, insertDiscussionTopic));
    }

    @Test
    void updateReply() {
        assertTrue(discussionReplyMongoRepository.updateReply("This is the upated reply", insertedReplyId, insertDiscussionTopic));
    }

    @Test
    void incrementLikes() {
        assertEquals(191, discussionReplyMongoRepository.incrementLikes(insertedReplyId, insertDiscussionTopic));
    }

    @Test
    void decrementLikes() {
        assertEquals(189, discussionReplyMongoRepository.decrementLikes(insertedReplyId, insertDiscussionTopic));
    }

    @Test
    void setInactive() {
        assertTrue(discussionReplyMongoRepository.setStatus(false, insertedReplyId, insertDiscussionTopic));
    }

    private void insertDiscussionTopicDocs() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setTitle("This a dummy discussion topic");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        discussionTopicDoc.setActive(true);
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId.toHexString(), "shabana"));
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setLikes(190);
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        mongoOperations.save(discussionTopicDoc);
        insertDiscussionTopic = discussionTopicDoc.getId();
        insertedReplyId = discussionReplyDoc.getId();
    }
}