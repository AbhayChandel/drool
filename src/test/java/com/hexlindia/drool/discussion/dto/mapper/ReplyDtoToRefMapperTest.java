package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.data.doc.ReplyRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReplyDtoToRefMapperTest {

    @Autowired
    ReplyDtoToRefMapper replyDtoToRefMapper;

    @Test
    void toReplyRef() {
        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        ObjectId replyId = ObjectId.get();
        discussionReplyDto.setId(replyId.toHexString());
        String reply = "This is going to be a great reply";
        discussionReplyDto.setReply(reply);
        ObjectId postId = ObjectId.get();
        discussionReplyDto.setPostRefDto(new PostRefDto(postId.toHexString(), "This is a test discussion", "discussion", "text", null));

        ReplyRef replyRef = replyDtoToRefMapper.toReplyRef(discussionReplyDto);
        assertEquals(replyId, replyRef.getId());
        assertEquals(reply, replyRef.getReply());
        assertEquals(postId, replyRef.getPostRef().getId());
        assertEquals("This is a test discussion", replyRef.getPostRef().getTitle());
        assertEquals("discussion", replyRef.getPostRef().getType());
        assertEquals("text", replyRef.getPostRef().getMedium());
    }
}