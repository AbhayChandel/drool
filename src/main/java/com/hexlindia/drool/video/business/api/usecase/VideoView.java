package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoPageView;

public interface VideoView {

    VideoCardView getVideoCardView(Long videoId);

    VideoPageView getVideoPageView(Long videoId);
}
