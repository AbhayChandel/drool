package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.video.data.doc.VideoDoc;

public interface VideoTemplateRepository {
    VideoDoc insert(VideoDoc videoDoc);

    VideoDoc findByIdAndActiveTrue(String id);

    void incrementLikes(String id);

    void decrementLikes(String id);
}
