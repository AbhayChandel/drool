package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VideoImpl implements Video {

    private final VideoDocDtoMapper videoDocDtoMapper;

    private final VideoTemplateRepository videoTemplateRepository;

    public VideoImpl(VideoDocDtoMapper videoDocDtoMapper, VideoTemplateRepository videoTemplateRepository) {
        this.videoDocDtoMapper = videoDocDtoMapper;
        this.videoTemplateRepository = videoTemplateRepository;
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
    public String incrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return videoTemplateRepository.incrementLikes(videoLikeUnlikeDto);
    }

    @Override
    public String decrementLikes(VideoLikeUnlikeDto videoLikeUnlikeDto) {
        return videoTemplateRepository.decrementLikes(videoLikeUnlikeDto);
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
