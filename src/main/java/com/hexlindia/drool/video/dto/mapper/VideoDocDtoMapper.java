package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductRefMapper.class, UserRefMapper.class, VideoCommentMapper.class})
public interface VideoDocDtoMapper {

    @Mapping(target = "productRef", source = "productRefDto")
    @Mapping(target = "userRef", source = "userRefDto")
    VideoDoc toDoc(VideoDto videoDto);

    @Mapping(target = "productRefDto", source = "productRef")
    @Mapping(target = "userRefDto", source = "userRef")
    @Mapping(target = "videoCommentDtoList", source = "videoCommentList")
    VideoDto toDto(VideoDoc videoDoc);
}
