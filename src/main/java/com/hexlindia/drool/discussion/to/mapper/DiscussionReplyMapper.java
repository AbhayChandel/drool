package com.hexlindia.drool.discussion.to.mapper;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscussionReplyMapper {

    @Mapping(target = "discussionReplyActivityEntity", source = "discussionReplyActivityTo")
    DiscussionReplyEntity toEntity(DiscussionReplyTo discussionReplyTo);

    @Mapping(target = "discussionReplyActivityTo", source = "discussionReplyActivityEntity")
    @Mapping(target = "discussionTopicId", source = "discussionTopicEntity.id")
    DiscussionReplyTo toTransferObject(DiscussionReplyEntity discussionReplyEntity);

    List<DiscussionReplyTo> toTransferObjectList(List<DiscussionReplyEntity> discussionReplyEntityList);
}
