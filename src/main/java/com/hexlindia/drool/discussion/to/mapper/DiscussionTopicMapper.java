package com.hexlindia.drool.discussion.to.mapper;


import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DiscussionReplyMapper.class})
public interface DiscussionTopicMapper {

    @Mapping(target = "discussionTopicActivityEntity", source = "discussionTopicActivityTo")
    DiscussionTopicEntity toEntity(DiscussionTopicTo discussionTopicTo);

    @Mapping(target = "discussionTopicActivityTo", source = "discussionTopicActivityEntity")
    @Mapping(target = "discussionReplyToList", source = "discussionReplyEntityList")
    DiscussionTopicTo toTransferObject(DiscussionTopicEntity discussionTopicEntity);


}
