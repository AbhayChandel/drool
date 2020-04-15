package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiscussionTopicDtoDocMapperTest {

    @Autowired
    private DiscussionTopicDtoDocMapper discussionTopicDtoDocMapper;

    @Test
    void toDoc() {
        DiscussionTopicDto discussionTopicDto = new DiscussionTopicDto();
        discussionTopicDto.setTitle("This is a new topic");
        ObjectId userId = ObjectId.get();
        discussionTopicDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));

        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        discussionReplyDto.setDiscussionId("123");
        discussionReplyDto.setReply("This is going to be a great reply");
        discussionReplyDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));

        discussionTopicDto.setDiscussionReplyDtoList(Arrays.asList(discussionReplyDto, new DiscussionReplyDto()));

        DiscussionTopicDoc discussionTopicDoc = discussionTopicDtoDocMapper.toDoc(discussionTopicDto);
        assertEquals("This is a new topic", discussionTopicDoc.getTitle());
        assertEquals(userId, discussionTopicDoc.getUserRef().getId());
        assertEquals("shabana", discussionTopicDoc.getUserRef().getUsername());
        assertEquals(2, discussionTopicDoc.getDiscussionReplyDocList().size());
        assertEquals("This is going to be a great reply", discussionTopicDoc.getDiscussionReplyDocList().get(0).getReply());
        assertEquals(userId, discussionTopicDoc.getDiscussionReplyDocList().get(0).getUserRef().getId());
        assertEquals("shabana", discussionTopicDoc.getDiscussionReplyDocList().get(0).getUserRef().getUsername());


    }

    @Test
    void toDto() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setTitle("This topic is returned from db");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId, "shabana"));
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);
        discussionTopicDoc.setDateLastActive(datePosted);
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setLikes(190);
        discussionReplyDoc.setDatePosted(LocalDateTime.now());
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        DiscussionTopicDto discussionTopicDto = discussionTopicDtoDocMapper.toDto(discussionTopicDoc);

        assertEquals("This topic is returned from db", discussionTopicDto.getTitle());
        assertEquals(userId.toHexString(), discussionTopicDto.getUserRefDto().getId());
        assertEquals("shabana", discussionTopicDto.getUserRefDto().getUsername());
        assertEquals(3, discussionTopicDto.getDiscussionReplyDtoList().size());
        assertEquals("As I told it is a great reply", discussionTopicDto.getDiscussionReplyDtoList().get(0).getReply());
        assertEquals(userId.toHexString(), discussionTopicDto.getDiscussionReplyDtoList().get(0).getUserRefDto().getId());
        assertEquals("shabana", discussionTopicDto.getDiscussionReplyDtoList().get(0).getUserRefDto().getUsername());
        assertEquals("190", discussionTopicDto.getDiscussionReplyDtoList().get(0).getLikes());
    }
}