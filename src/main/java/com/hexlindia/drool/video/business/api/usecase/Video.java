package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;

public interface Video {

    public VideoDto insert(VideoDto videoDto);

    public VideoDto findById(String id);

    public boolean incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    public boolean decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);
}
