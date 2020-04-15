package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDataDto;
import org.bson.types.ObjectId;

public interface Video {

    VideoDto save(VideoDto videoDto);

    VideoDto findById(String id);

    String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoCommentDto insertComment(VideoCommentDto videoCommentDto);

    boolean deleteComment(VideoCommentDto videoCommentDto);

    String saveCommentLike(VideoCommentDto videoCommentDto);

    String deleteCommentLike(VideoCommentDto videoCommentDto);

    VideoThumbnailDataDto getLatestThreeVideoThumbnails(String userId);

    boolean updateReviewId(ObjectId videoId, ObjectId reviewId);
}
