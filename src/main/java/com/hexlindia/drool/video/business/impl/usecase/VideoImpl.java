package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import com.hexlindia.drool.video.dto.*;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.dto.mapper.VideoThumbnailDataMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class VideoImpl implements Video {

    private final VideoDocDtoMapper videoDocDtoMapper;

    private final VideoRepository videoRepository;

    private final VideoCommentMapper videoCommentMapper;

    private final PostRefMapper postRefMapper;

    private final UserActivity userActivity;

    private final VideoThumbnailDataMapper videoThumbnailDataMapper;

    private final ActivityFeed activityFeed;


    @Override
    public VideoDto save(VideoDto videoDto) {
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDto);
        videoDoc = videoRepository.save(videoDoc);
        if (videoDoc.getId() != null) {
            userActivity.addVideo(videoDoc);
            activityFeed.addVideo(videoDoc);
            return videoDocDtoMapper.toDto(videoDoc);
        }
        log.error("Video not saved");
        return null;
    }

    @Override
    public VideoDto findById(String id) {
        Optional<VideoDoc> videoDocOptional = videoRepository.findByIdAndActiveTrue(new ObjectId(id));
        if (videoDocOptional.isPresent()) {
            return videoDocDtoMapper.toDto(videoDocOptional.get());
        }
        throw new VideoNotFoundException("Video with Id " + id + " not found");
    }

    @Override
    public VideoThumbnailDataDto getLatestThreeVideoThumbnails(String userId) {
        VideoThumbnailDataAggregation videoThumbnailDataAggregation = videoRepository.getLatestThreeVideosByUser(new ObjectId(userId));
        return videoThumbnailDataMapper.toDto(videoThumbnailDataAggregation);

    }

    @Override
    public String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        String likes = videoRepository.saveVideoLikes(videoLikeUnlikeDto);
        userActivity.addVideoLike(videoLikeUnlikeDto);
        activityFeed.setField(new ObjectId(videoLikeUnlikeDto.getVideoId()), FeedDocFields.likes, likes);
        return likes;
    }

    @Override
    public String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        String likes = videoRepository.deleteVideoLikes(videoLikeUnlikeDto);
        userActivity.deleteVideoLike(videoLikeUnlikeDto);
        activityFeed.setField(new ObjectId(videoLikeUnlikeDto.getVideoId()), FeedDocFields.likes, likes);
        return likes;
    }

    @Override
    public VideoCommentDto insertOrUpdateComment(VideoCommentDto videoCommentDto) {
        if (videoCommentDto.getId() != null) {
            return updateComment(videoCommentDto);
        }
        return insertComment(videoCommentDto);
    }

    private VideoCommentDto updateComment(VideoCommentDto videoCommentDto) {
        videoCommentDto = videoRepository.updateComment(videoCommentDto);
        userActivity.updateVideoComment(new ObjectId(videoCommentDto.getUserRefDto().getId()), new CommentRef(new ObjectId(videoCommentDto.getId()), videoCommentDto.getComment(), postRefMapper.toDoc(videoCommentDto.getPostRefDto()), null));
        return videoCommentDto;
    }

    private VideoCommentDto insertComment(VideoCommentDto videoCommentDto) {
        PostRef postRef = postRefMapper.toDoc(videoCommentDto.getPostRefDto());
        VideoComment videoComment = videoCommentMapper.toDoc(videoCommentDto);
        videoCommentDto = videoCommentMapper.toDto(videoRepository.insertComment(postRef, videoComment));
        if (videoCommentDto != null) {
            userActivity.addVideoComment(videoComment.getUserRef().getId(), new CommentRef(videoComment.getId(), videoComment.getComment(), postRef, videoComment.getDatePosted()));
            activityFeed.incrementDecrementField(postRef.getId(), FeedDocFields.comments, 1);
            return videoCommentDto;
        }
        log.warn("Video comment not inserted");
        return null;
    }

    @Override
    public boolean deleteComment(VideoCommentDto videoCommentDto) {
        boolean result = videoRepository.deleteComment(videoCommentDto);
        if (result) {
            userActivity.deleteVideoComment(videoCommentDto);
            activityFeed.incrementDecrementField(new ObjectId(videoCommentDto.getPostRefDto().getId()), FeedDocFields.comments, -1);
            return true;
        }
        log.warn("Video comment not deleted");
        return false;
    }

    @Override
    public String saveCommentLike(VideoCommentDto videoCommentDto) {
        String likes = videoRepository.saveCommentLike(videoCommentDto);
        if (Integer.valueOf(likes) > Integer.valueOf(videoCommentDto.getLikes())) {
            userActivity.addCommentLike(videoCommentDto);
        } else {
            log.error("Video comment like not saved");
        }
        return likes;
    }

    @Override
    public String deleteCommentLike(VideoCommentDto videoCommentDto) {
        String likes = videoRepository.deleteCommentLike(videoCommentDto);
        if (Integer.valueOf(likes) < Integer.valueOf(videoCommentDto.getLikes())) {
            userActivity.deleteCommentLike(videoCommentDto);
        } else {
            log.error("Video comment like not deleted");
        }
        return likes;
    }
}
