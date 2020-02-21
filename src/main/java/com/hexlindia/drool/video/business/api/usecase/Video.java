package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface Video {

    public VideoDto insert(VideoDto videoDto);

    public VideoDto findById(String id);

    public String incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    public String decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoCommentDto insertComment(VideoCommentDto videoCommentDto);
}
