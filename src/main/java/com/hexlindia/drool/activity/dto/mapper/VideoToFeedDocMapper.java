package com.hexlindia.drool.activity.dto.mapper;

import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class VideoToFeedDocMapper {

    @Mapping(source = "type", target = "postType")
    public abstract FeedDoc toFeedDoc(VideoDoc videoDoc);

    @AfterMapping
    protected void afterConversion(VideoDoc videoDoc, @MappingTarget FeedDoc feedDoc) {
        feedDoc.setPostMedium("video");
    }
}
