package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;

public interface UserActivityRepository {

    UpdateResult addVideo(VideoDoc videoDoc);

    UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);

    UpdateResult removeVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);

    UpdateResult addVideoComment(String userId, CommentRef commentRef);

    UpdateResult removeVideoComment(VideoCommentDto videoCommentDto);

    UpdateResult addCommentLike(VideoCommentDto videoCommentDto);

    UpdateResult deleteCommentLike(VideoCommentDto videoCommentDto);
}
