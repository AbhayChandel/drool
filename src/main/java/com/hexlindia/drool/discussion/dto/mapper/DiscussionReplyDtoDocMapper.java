package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, UserRefMapper.class})
public abstract class DiscussionReplyDtoDocMapper {


    @Mapping(source = "userRefDto", target = "userRef")
    public abstract DiscussionReplyDoc toDoc(DiscussionReplyDto discussionReplyDto);

    public abstract List<DiscussionReplyDoc> toDocList(List<DiscussionReplyDto> discussionReplyDtoList);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(source = "userRef", target = "userRefDto")
    public abstract DiscussionReplyDto toDto(DiscussionReplyDoc discussionReplyDoc);

    public abstract List<DiscussionReplyDto> toDtoList(List<DiscussionReplyDoc> discussionReplyDocList);

    @AfterMapping
    protected void afterMappingToDto(DiscussionReplyDoc discussionReplyDoc, @MappingTarget DiscussionReplyDto discussionReplyDto) {
        if (discussionReplyDoc.getDatePosted() != null) {
            discussionReplyDto.setDatePosted(MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(discussionReplyDoc.getDatePosted()));
        }
        discussionReplyDto.setLikes(MetaFieldValueFormatter.getCompactFormat(discussionReplyDoc.getLikes()));
    }

}
