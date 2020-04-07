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
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.*;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.dto.mapper.VideoThumbnailDataMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VideoImpl implements Video {

    private final VideoDocDtoMapper videoDocDtoMapper;

    private final VideoTemplateRepository videoTemplateRepository;

    private final VideoCommentMapper videoCommentMapper;

    private final PostRefMapper postRefMapper;

    private final UserActivity userActivity;

    private final VideoThumbnailDataMapper videoThumbnailDataMapper;

    private final ActivityFeed activityFeed;


    @Override
    public VideoDto save(VideoDto videoDto) {
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDto);
        videoDoc = videoTemplateRepository.save(videoDoc);
        if (videoDoc.getId() != null) {
            userActivity.addVideo(videoDoc);
            activityFeed.addVideo(videoDoc);
            return videoDocDtoMapper.toDto(videoDoc);
        }
        log.error("Video not inserted");
        return null;
    }

    @Override
    public VideoDto findById(String id) {
        return videoDocDtoMapper.toDto(findInRepository("Video search", id));
    }

    @Override
    public VideoThumbnailDataDto getLatestThreeVideoThumbnails(String userId) {
        VideoThumbnailDataAggregation videoThumbnailDataAggregation = videoTemplateRepository.getLatestThreeVideosByUser(userId);
        return videoThumbnailDataMapper.toDto(videoThumbnailDataAggregation);

    }

    @Override
    public boolean updateReviewId(ObjectId videoId, ObjectId reviewId) {
        return videoTemplateRepository.updateReviewId(videoId, reviewId);
    }

    @Override
    public String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        String likes = videoTemplateRepository.saveVideoLikes(videoLikeUnlikeDto);
        userActivity.addVideoLike(videoLikeUnlikeDto);
        activityFeed.setField(new ObjectId(videoLikeUnlikeDto.getVideoId()), FeedDocFields.likes, likes);
        return likes;
    }

    @Override
    public String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        String likes = videoTemplateRepository.deleteVideoLikes(videoLikeUnlikeDto);
        userActivity.deleteVideoLike(videoLikeUnlikeDto);
        activityFeed.setField(new ObjectId(videoLikeUnlikeDto.getVideoId()), FeedDocFields.likes, likes);
        return likes;
    }

    @Override
    public VideoCommentDto insertComment(VideoCommentDto videoCommentDto) {
        PostRef postRef = postRefMapper.toDoc(videoCommentDto.getPostRefDto());
        VideoComment videoComment = videoCommentMapper.toDoc(videoCommentDto);
        videoCommentDto = videoCommentMapper.toDto(videoTemplateRepository.insertComment(postRef, videoComment));
        if (videoCommentDto != null) {
            userActivity.addVideoComment(videoComment.getUserRef().getId(), new CommentRef(videoComment.getId(), videoComment.getComment(), postRef, videoComment.getDatePosted()));
            activityFeed.incrementDecrementField(new ObjectId(videoCommentDto.getId()), FeedDocFields.comments, 1);
            return videoCommentDto;
        }
        log.error("Video comment not inserted");
        return null;
    }

    @Override
    public boolean deleteComment(VideoCommentDto videoCommentDto) {
        boolean result = videoTemplateRepository.deleteComment(videoCommentDto);
        if (result) {
            userActivity.deleteVideoComment(videoCommentDto);
            activityFeed.incrementDecrementField(new ObjectId(videoCommentDto.getId()), FeedDocFields.comments, -1);
        } else {
            log.error("Video comment not deleted");
        }
        return result;
    }

    @Override
    public String saveCommentLike(VideoCommentDto videoCommentDto) {
        String likes = videoTemplateRepository.saveCommentLike(videoCommentDto);
        if (Integer.valueOf(likes) > Integer.valueOf(videoCommentDto.getLikes())) {
            userActivity.addCommentLike(videoCommentDto);
        } else {
            log.error("Video comment like not saved");
        }
        return likes;
    }

    @Override
    public String deleteCommentLike(VideoCommentDto videoCommentDto) {
        String likes = videoTemplateRepository.deleteCommentLike(videoCommentDto);
        if (Integer.valueOf(likes) < Integer.valueOf(videoCommentDto.getLikes())) {
            userActivity.deleteCommentLike(videoCommentDto);
        } else {
            log.error("Video comment like not deleted");
        }
        return likes;
    }

    private VideoDoc findInRepository(String action, String id) {
        VideoDoc videoDoc = videoTemplateRepository.findByIdAndActiveTrue(id);
        if (videoDoc != null) {
            return videoDoc;
        }
        StringBuilder errorMessage = new StringBuilder(action);
        errorMessage.append(" failed. Video with id " + id + " not found");
        throw new VideoNotFoundException(errorMessage.toString());
    }
}
