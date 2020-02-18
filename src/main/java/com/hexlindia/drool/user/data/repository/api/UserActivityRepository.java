package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;

public interface UserActivityRepository {

    UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);

    UpdateResult removeVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);
}
