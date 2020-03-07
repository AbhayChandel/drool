package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.dto.VideoThumbnailDataAggregation;
import com.hexlindia.drool.video.dto.VideoThumbnailDataDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VideoThumbnailMapper.class})
public abstract class VideoThumbnailDataMapper {

    public abstract VideoThumbnailDataDto toDto(VideoThumbnailDataAggregation videoThumbnailDataAggregation);

    @AfterMapping
    protected void thisIsCalledAfterMappingIsDone(VideoThumbnailDataAggregation videoThumbnailDataAggregation, @MappingTarget VideoThumbnailDataDto videoThumbnailDataDto) {
        if (videoThumbnailDataAggregation.getTotalVideoCount() == null) {
            videoThumbnailDataDto.setTotalVideoCount(0);
        }
    }
}
