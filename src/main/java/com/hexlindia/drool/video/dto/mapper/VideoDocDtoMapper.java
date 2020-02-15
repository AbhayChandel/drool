package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductRefMapper.class, UserRefMapper.class, VideoCommentMapper.class})
public abstract class VideoDocDtoMapper {

    @Mapping(target = "productRefList", source = "productRefDtoList")
    @Mapping(target = "userRef", source = "userRefDto")
    public abstract VideoDoc toDoc(VideoDto videoDto);

    @Mapping(target = "productRefDtoList", source = "productRefList")
    @Mapping(target = "userRefDto", source = "userRef")
    @Mapping(target = "videoCommentDtoList", source = "videoCommentList")
    public abstract VideoDto toDto(VideoDoc videoDoc);

    @AfterMapping
    protected void thisIsCalledBeforeMappingIsDone(VideoDoc videoDoc, @MappingTarget VideoDto videoDto) {
        videoDto.setLikes(MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes()));
        videoDto.setViews(MetaFieldValueFormatter.getCompactFormat(videoDoc.getViews()));
    }
}
