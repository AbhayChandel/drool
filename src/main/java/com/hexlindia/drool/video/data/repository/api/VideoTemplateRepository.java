package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface VideoTemplateRepository {
    VideoDoc insert(VideoDoc videoDoc);

    VideoDoc findByIdAndActiveTrue(String id);

    boolean incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    boolean decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);
}
