package com.hexlindia.drool.video.data.repository.api;

import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoCommentCardView;

import java.util.List;

public interface VideoViewRepository {

    VideoCardView getVideoCardView(Long videoId);

    List<VideoCommentCardView> getVideoCommentCardViews(Long videoId);

}
