package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

public interface UserActivityRepository {

    UpdateResult addVideo(VideoDoc videoDoc);

    UpdateResult addVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);

    UpdateResult deleteVideoLike(VideoLikeUnlikeDto videoLikeUnlikeDto);

    UpdateResult addVideoComment(ObjectId userId, CommentRef commentRef);

    UpdateResult updateVideoComment(ObjectId userId, CommentRef commentRef);

    UpdateResult deleteVideoComment(VideoCommentDto videoCommentDto);

    UpdateResult addCommentLike(VideoCommentDto videoCommentDto);

    UpdateResult deleteCommentLike(VideoCommentDto videoCommentDto);

    UpdateResult addTextReview(ReviewDoc reviewDoc);
}
