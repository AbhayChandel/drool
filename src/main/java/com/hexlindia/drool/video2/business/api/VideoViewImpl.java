package com.hexlindia.drool.video2.business.api;

import com.hexlindia.drool.video2.data.repository.api.VideoViewRepository;
import com.hexlindia.drool.video2.view.VideoPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoViewImpl implements VideoView {

    private final VideoViewRepository videoViewRepository;

    @Override
    public List<VideoPreview> getVideoPreviews(List<Integer> idList) {
        return videoViewRepository.getVideoPreviews(idList);
    }
}
