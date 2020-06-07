package com.hexlindia.drool.feed.view.mapper;

import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DiscussionFeedPreviewMapper {

    @Mapping(source = "replies", target = "comments")
    public abstract FeedItemPreview toFeedPreview(DiscussionPreview discussionPreview);

    public abstract List<FeedItemPreview> toFeedPreviewList(List<DiscussionPreview> discussionPreviewList);

    @AfterMapping
    protected void afterMappingToDto(@MappingTarget FeedItemPreview feedItemPreview) {
        feedItemPreview.setItemType("discussion");
    }
}
