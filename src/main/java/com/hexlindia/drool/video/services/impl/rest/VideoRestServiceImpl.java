package com.hexlindia.drool.video.services.impl.rest;

import com.hexlindia.drool.common.dto.validation.PostRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.PostRefValidation;
import com.hexlindia.drool.common.dto.validation.UserRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.UserRefValidation;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.validation.*;
import com.hexlindia.drool.video.services.api.rest.VideoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoRestServiceImpl implements VideoRestService {

    private final Video video;

    @Autowired
    public VideoRestServiceImpl(Video video) {
        this.video = video;
    }

    @Override
    public ResponseEntity<VideoDto> save(@Validated(VideoInsertValidation.class) VideoDto videoDto) {
        return ResponseEntity.ok(video.saveOrUpdate(videoDto));
    }

    @Override
    public ResponseEntity<Boolean> delete(VideoDto videoDto) {
        return ResponseEntity.ok(video.delete(videoDto));
    }

    @Override
    public ResponseEntity<String> incrementVideoLikes(@Validated(VideoIncrementLikesValidation.class) VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return ResponseEntity.ok(video.incrementVideoLikes(videoLikeUnlikeDto));
    }

    @Override
    public ResponseEntity<String> decrementVideoLikes(@Validated(VideoDecrementLikesValidation.class) VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return ResponseEntity.ok(video.decrementVideoLikes(videoLikeUnlikeDto));
    }

    @Override
    public ResponseEntity<VideoCommentDto> insertComment(@Validated({VideoCommentInsertValidation.class, PostRefValidation.class, UserRefValidation.class}) VideoCommentDto videoCommentDto) {
        return ResponseEntity.ok(video.insertOrUpdateComment(videoCommentDto));
    }

    @Override
    public ResponseEntity<Boolean> deleteComment(@Validated({VideoCommentDeleteValidation.class, PostRefDeleteValidation.class, UserRefDeleteValidation.class}) VideoCommentDto videoCommentDto) {
        return ResponseEntity.ok(video.deleteComment(videoCommentDto));
    }

    @Override
    public ResponseEntity<String> saveCommentLike(@Validated({VideoCommentRefValidation.class, VideoCommentRefLikeValidation.class, PostRefValidation.class, UserRefValidation.class}) VideoCommentDto videoCommentDto) {
        return ResponseEntity.ok(video.saveCommentLike(videoCommentDto));
    }

    @Override
    public ResponseEntity<String> deleteCommentLike(@Validated({VideoCommentDeleteValidation.class, PostRefDeleteValidation.class, UserRefDeleteValidation.class, VideoCommentRefLikeValidation.class}) VideoCommentDto videoCommentDto) {
        return ResponseEntity.ok(video.deleteCommentLike(videoCommentDto));
    }


}
