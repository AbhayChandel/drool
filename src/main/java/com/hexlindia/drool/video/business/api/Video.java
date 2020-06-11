package com.hexlindia.drool.video.business.api;

import com.hexlindia.drool.video.dto.*;

public interface Video {

    VideoDtoMOngo saveOrUpdate(VideoDto videoDto);

    boolean delete(VideoDto videoDto);

    VideoDtoMOngo findById(String id);

    String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoCommentDto insertOrUpdateComment(VideoCommentDto videoCommentDto);

    boolean deleteComment(VideoCommentDto videoCommentDto);

    String saveCommentLike(VideoCommentDto videoCommentDto);

    String deleteCommentLike(VideoCommentDto videoCommentDto);

    VideoThumbnailDataDto getLatestThreeVideoThumbnails(String userId);
}
