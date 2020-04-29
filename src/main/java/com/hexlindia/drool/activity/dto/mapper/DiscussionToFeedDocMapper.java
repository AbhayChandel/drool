package com.hexlindia.drool.activity.dto.mapper;

import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class DiscussionToFeedDocMapper {

    @Mapping(source = "repliesCount", target = "comments")
    public abstract FeedDoc toFeedDoc(DiscussionTopicDoc discussionTopicDoc);

    @AfterMapping
    protected void afterConversion(DiscussionTopicDoc discussionTopicDoc, @MappingTarget FeedDoc feedDoc) {
        feedDoc.setPostType("discussion");
        feedDoc.setPostMedium("text");
    }
}
