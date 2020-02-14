package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VideoImpl implements Video {

    private final VideoDocDtoMapper videoDocDtoMapper;

    private final VideoRepository videoRepository;

    public VideoImpl(VideoDocDtoMapper videoDocDtoMapper, VideoRepository videoRepository) {
        this.videoDocDtoMapper = videoDocDtoMapper;
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoDto insert(VideoDto videoDto) {
        return videoDocDtoMapper.toDto(videoRepository.insert(videoDocDtoMapper.toDoc(videoDto)));
    }

    @Override
    public VideoDto findById(String id) {
        return videoDocDtoMapper.toDto(findInRepository("Video search", id));
    }

    private VideoDoc findInRepository(String action, String id) {
        Optional<VideoDoc> videoDocOptional = videoRepository.findById(id);
        if (videoDocOptional.isPresent()) {
            return videoDocOptional.get();
        }
        StringBuilder errorMessage = new StringBuilder(action);
        errorMessage.append(" failed. Video with id " + id + " not found");
        throw new VideoNotFoundException(errorMessage.toString());
    }
}
