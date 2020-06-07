package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.common.dto.mapper.LocalDateTimeMapper;
import com.hexlindia.drool.common.dto.mapper.StringToLocalDateTimeMapping;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import com.hexlindia.drool.video2.view.VideoPreview;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocalDateTimeMapper.class})
public abstract class VideoFeedPreviewMapper {

    @Mapping(target = "datePosted", source = "datePosted", qualifiedBy = StringToLocalDateTimeMapping.class)
    public abstract FeedItemPreview toFeedPreview(VideoPreview videoPreview);

    public abstract List<FeedItemPreview> toFeedPreviewList(List<VideoPreview> videoPreviewList);

    @AfterMapping
    protected void afterMappingToDto(@MappingTarget FeedItemPreview feedItemPreview) {
        feedItemPreview.setItemType("video");
    }
}
