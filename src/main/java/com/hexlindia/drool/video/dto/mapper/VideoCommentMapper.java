package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import org.bson.types.ObjectId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserRefMapper.class, ObjectIdMapper.class})
public abstract class VideoCommentMapper {

    @Mapping(target = "userRefDto", source = "userRef")
    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    public abstract VideoCommentDto toDto(VideoComment videoComment);

    abstract List<VideoCommentDto> toDtoList(List<VideoComment> commentList);

    @Mapping(target = "userRef", source = "userRefDto")
    @Mapping(source = "id", target = "id", ignore = true)
    public abstract VideoComment toDoc(VideoCommentDto videoCommentDto);

    @AfterMapping
    protected void thisIsCalledAfterMappingDocToDto(VideoComment videoComment, @MappingTarget VideoCommentDto videoCommentDto) {
        videoCommentDto.setLikes(MetaFieldValueFormatter.getCompactFormat(videoComment.getLikes()));
        videoCommentDto.setDatePosted(MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(videoComment.getDatePosted()));
    }

    @AfterMapping
    protected void thisIsCalledAfterMappingDtoToDoc(VideoCommentDto videoCommentDto, @MappingTarget VideoComment videoComment) {
        if (videoCommentDto.getId() != null) {
            videoComment.setId(new ObjectId(videoCommentDto.getId()));
        }
    }
}
