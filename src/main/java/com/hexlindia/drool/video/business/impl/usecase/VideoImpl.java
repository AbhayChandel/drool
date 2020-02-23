package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VideoImpl implements Video {

    private final VideoDocDtoMapper videoDocDtoMapper;

    private final VideoTemplateRepository videoTemplateRepository;

    private final VideoCommentMapper videoCommentMapper;

    private final PostRefMapper postRefMapper;

    public VideoImpl(VideoDocDtoMapper videoDocDtoMapper, VideoTemplateRepository videoTemplateRepository, VideoCommentMapper videoCommentMapper, PostRefMapper postRefMapper) {
        this.videoDocDtoMapper = videoDocDtoMapper;
        this.videoTemplateRepository = videoTemplateRepository;
        this.videoCommentMapper = videoCommentMapper;
        this.postRefMapper = postRefMapper;
    }

    @Override
    public VideoDto insert(VideoDto videoDto) {
        VideoDoc videoDoc = videoDocDtoMapper.toDoc(videoDto);
        videoDoc.setDatePosted(LocalDateTime.now());
        return videoDocDtoMapper.toDto(videoTemplateRepository.insert(videoDoc));
    }

    @Override
    public VideoDto findById(String id) {
        return videoDocDtoMapper.toDto(findInRepository("Video search", id));
    }

    @Override
    public String incrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return videoTemplateRepository.saveVideoLikes(videoLikeUnlikeDto);
    }

    @Override
    public String decrementVideoLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return videoTemplateRepository.deleteVideoLikes(videoLikeUnlikeDto);
    }

    @Override
    public VideoCommentDto insertComment(VideoCommentDto videoCommentDto) {
        return videoCommentMapper.toDto(videoTemplateRepository.insertComment(postRefMapper.toDoc(videoCommentDto.getPostRefDto()), videoCommentMapper.toDoc(videoCommentDto)));
    }

    @Override
    public boolean deleteComment(VideoCommentDto videoCommentDto) {
        return videoTemplateRepository.deleteComment(videoCommentDto);
    }

    @Override
    public String saveCommentLike(VideoCommentDto videoCommentDto) {
        return videoTemplateRepository.saveCommentLike(videoCommentDto);
    }

    @Override
    public String deleteCommentLike(VideoCommentDto videoCommentDto) {
        return videoTemplateRepository.deleteCommentLike(videoCommentDto);
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
