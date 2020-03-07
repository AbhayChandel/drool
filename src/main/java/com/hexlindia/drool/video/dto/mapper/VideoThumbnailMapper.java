package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.video.dto.VideoThumbnail;
import com.hexlindia.drool.video.dto.VideoThumbnailDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class VideoThumbnailMapper {

    public abstract VideoThumbnailDto toDto(VideoThumbnail videoThumbnail);

    @AfterMapping
    protected void thisIsCalledAfterMappingIsDone(VideoThumbnail videoThumbnail, @MappingTarget VideoThumbnailDto videoThumbnailDto) {
        videoThumbnailDto.setLikes(MetaFieldValueFormatter.getCompactFormat(videoThumbnail.getLikes()));
        videoThumbnailDto.setViews(MetaFieldValueFormatter.getCompactFormat(videoThumbnail.getViews()));
    }
}
