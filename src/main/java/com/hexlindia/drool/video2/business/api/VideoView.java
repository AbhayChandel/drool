package com.hexlindia.drool.video2.business.api;

import com.hexlindia.drool.video2.view.VideoPreview;

import java.util.List;

public interface VideoView {

    List<VideoPreview> getVideoPreviews(List<Integer> idList);
}
