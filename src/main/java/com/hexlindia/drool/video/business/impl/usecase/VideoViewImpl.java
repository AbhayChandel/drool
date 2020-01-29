package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.video.business.api.usecase.VideoView;
import com.hexlindia.drool.video.data.repository.api.VideoViewRepository;
import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoPageView;
import org.springframework.stereotype.Component;

@Component
public class VideoViewImpl implements VideoView {

    private final VideoViewRepository videoViewRepository;

    public VideoViewImpl(VideoViewRepository videoViewRepository) {
        this.videoViewRepository = videoViewRepository;
    }

    @Override
    public VideoCardView getVideoCardView(Long videoId) {
        return videoViewRepository.getVideoCardView(videoId);
    }

    @Override
    public VideoPageView getVideoPageView(Long videoId) {
        return new VideoPageView(videoViewRepository.getVideoCardView(videoId), videoViewRepository.getVideoCommentCardViews(videoId));
    }
}
