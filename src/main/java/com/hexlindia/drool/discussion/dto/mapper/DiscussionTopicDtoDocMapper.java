package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, DiscussionReplyDtoDocMapper.class, UserRefMapper.class})
public abstract class DiscussionTopicDtoDocMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    @Mapping(source = "userRefDto", target = "userRef")
    @Mapping(source = "discussionReplyDtoList", target = "discussionReplyDocList")
    public abstract DiscussionTopicDoc toDoc(DiscussionTopicDto discussionTopicDto);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(source = "userRef", target = "userRefDto")
    @Mapping(source = "discussionReplyDocList", target = "discussionReplyDtoList")
    @Mapping(source = "repliesCount", target = "replies")
    public abstract DiscussionTopicDto toDto(DiscussionTopicDoc discussionTopicDoc);

    @AfterMapping
    protected void afterMappingToDto(DiscussionTopicDoc discussionTopicDoc, @MappingTarget DiscussionTopicDto discussionTopicDto) {
        discussionTopicDto.setDatePosted(MetaFieldValueFormatter.getDateInDayMonCommaYear(discussionTopicDoc.getDatePosted()));
        discussionTopicDto.setDateLastActive(MetaFieldValueFormatter.getDateInDayMonCommaYear(discussionTopicDoc.getDateLastActive()));
        discussionTopicDto.setLikes(MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes()));
        discussionTopicDto.setViews(MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getViews()));
        if (discussionTopicDoc.getDiscussionReplyDocList() == null) {
            discussionTopicDto.setDiscussionReplyDtoList(new ArrayList<>());
        }
    }
}
