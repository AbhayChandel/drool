package com.hexlindia.drool.discussion.business.api.to.mapper;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import com.hexlindia.drool.discussion.to.mapper.DiscussionTopicMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscussionTopicMapperTest {

    private final DiscussionTopicMapper mapper = Mappers.getMapper(DiscussionTopicMapper.class);

    @Test
    void toEntity() {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(10L, "This is a dummy topic", 3L);
        DiscussionTopicEntity discussionTopicEntity = mapper.toEntity(discussionTopicTo);
        assertEquals(10L, discussionTopicEntity.getId());
        assertEquals("This is a dummy topic", discussionTopicEntity.getTopic());
        assertEquals(3L, discussionTopicEntity.getUserId());
    }

    @Test
    void toTransferObject() {
        DiscussionTopicEntity discussionTopicEntity = new DiscussionTopicEntity();
        discussionTopicEntity.setId(20L);
        discussionTopicEntity.setTopic("This is also a dummy topic");
        discussionTopicEntity.setUserId(6L);
        DiscussionTopicTo discussionTopicTo = mapper.toTransferObject(discussionTopicEntity);
        assertEquals(20L, discussionTopicTo.getId());
        assertEquals("This is also a dummy topic", discussionTopicTo.getTopic());
        assertEquals(6L, discussionTopicTo.getUserId());
    }
}