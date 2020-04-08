package com.hexlindia.drool.activity.business.api.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActivityFeed {

    public void addVideo(VideoDoc videoDoc);

    public void setField(ObjectId postId, FeedDocFields feedDocFields, String value);

    void incrementDecrementField(ObjectId postId, FeedDocFields feedDocFields, int value);

    List<FeedDto> getFeed(int page);
}
