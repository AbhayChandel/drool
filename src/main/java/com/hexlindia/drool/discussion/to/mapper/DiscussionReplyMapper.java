package com.hexlindia.drool.discussion.to.mapper;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscussionReplyMapper {

    DiscussionReplyEntity toEntity(DiscussionReplyTo discussionReplyTo);

    @Mapping(target = "discussionTopicId", source = "discussionTopicEntity.id")
    @Mapping(target = "datePosted", source = "datePosted", dateFormat = "dd-MMM-yyyy hh:mm a")
    DiscussionReplyTo toTransferObject(DiscussionReplyEntity discussionReplyEntity);

    List<DiscussionReplyTo> toTransferObjectList(List<DiscussionReplyEntity> discussionReplyEntityList);
}
