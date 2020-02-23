package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface VideoTemplateRepository {
    VideoDoc insert(VideoDoc videoDoc);

    VideoDoc findByIdAndActiveTrue(String id);

    String saveVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    String deleteVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoComment insertComment(PostRef postRef, VideoComment videoComment);

    boolean deleteComment(VideoCommentDto videoCommentDto);

    String saveCommentLike(VideoCommentDto videoCommentDto);

    String deleteCommentLike(VideoCommentDto videoCommentDto);
}
