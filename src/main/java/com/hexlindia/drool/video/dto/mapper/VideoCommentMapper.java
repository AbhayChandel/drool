package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VideoCommentMapper {

    @Mapping(target = "userRefDto", source = "userRef")
    abstract VideoCommentDto toDto(VideoComment videoComment);

    abstract List<VideoCommentDto> toDtoList(List<VideoComment> videoCommentList);

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
            videoComment.setId(videoCommentDto.getId());
        }
    }
}
