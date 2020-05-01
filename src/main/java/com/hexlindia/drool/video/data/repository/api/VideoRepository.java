package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDataAggregation;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface VideoRepository {
    VideoDoc save(VideoDoc videoDoc);

    Optional<VideoDoc> findByIdAndActiveTrue(ObjectId id);

    String saveVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    String deleteVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto);

    VideoComment insertComment(PostRef postRef, VideoComment videoComment);

    VideoCommentDto updateComment(VideoCommentDto videoCommentDto);

    boolean deleteComment(VideoCommentDto videoCommentDto);

    String saveCommentLike(VideoCommentDto videoCommentDto);

    String deleteCommentLike(VideoCommentDto videoCommentDto);

    VideoThumbnailDataAggregation getLatestThreeVideosByUser(ObjectId userId);

    boolean updateVideo(VideoDoc videoDoc);

    DeleteResult deleteVideo(ObjectId id);
}
