package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface Video {

    public VideoDto insert(VideoDto videoDto);

    public VideoDto findById(String id);

    public String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    public String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoCommentDto insertComment(VideoCommentDto videoCommentDto);

    boolean deleteComment(VideoCommentDto videoCommentDto);

    String saveCommentLike(VideoCommentDto videoCommentDto);

    String deleteCommentLike(VideoCommentDto videoCommentDto);
}
