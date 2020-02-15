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

    @AfterMapping
    protected void thisIsCalledBeforeMappingIsDone(VideoComment videoComment, @MappingTarget VideoCommentDto videoCommentDto) {
        videoCommentDto.setLikes(MetaFieldValueFormatter.getCompactFormat(videoComment.getLikes()));
        videoCommentDto.setDatePosted(MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(videoComment.getDatePosted()));
    }
}
