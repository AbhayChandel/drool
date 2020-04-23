package com.hexlindia.drool.discussion.dto.mapper;

import com.hexlindia.drool.common.data.doc.ReplyRef;
import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, PostRefMapper.class})
public interface ReplyDtoToRefMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    @Mapping(source = "postRefDto", target = "postRef")
    ReplyRef toReplyRef(DiscussionReplyDto discussionReplyDto);
}
