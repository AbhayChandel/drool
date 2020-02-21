package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface VideoTemplateRepository {
    VideoDoc insert(VideoDoc videoDoc);

    VideoDoc findByIdAndActiveTrue(String id);

    String incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    String decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    boolean insertComment(PostRef postRef, VideoComment videoComment);
}
