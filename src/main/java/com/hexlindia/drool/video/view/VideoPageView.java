package com.hexlindia.drool.video.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VideoPageView {

    @JsonProperty("videoCard")
    private final VideoCardView videoCardView;

    @JsonProperty("commentCardList")
    private final List<VideoCommentCardView> videoCommentCardViewList;

    public VideoPageView(VideoCardView videoCardView, List<VideoCommentCardView> videoCommentCardViewList) {
        this.videoCardView = videoCardView;
        this.videoCommentCardViewList = videoCommentCardViewList;
    }

    public VideoCardView getVideoCardView() {
        return videoCardView;
    }

    public List<VideoCommentCardView> getVideoCommentCardViewList() {
        return videoCommentCardViewList;
    }
}
