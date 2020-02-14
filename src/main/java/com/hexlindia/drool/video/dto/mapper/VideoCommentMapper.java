package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoCommentMapper {

    @Mapping(target = "userRefDto", source = "userRef")
    VideoCommentDto toDto(VideoComment videoComment);

    List<VideoCommentDto> toDtoList(List<VideoComment> videoCommentList);
}
