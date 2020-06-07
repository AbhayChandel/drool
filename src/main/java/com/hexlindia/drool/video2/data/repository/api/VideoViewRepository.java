package com.hexlindia.drool.video2.data.repository.api;

import com.hexlindia.drool.video2.view.VideoPreview;

import java.util.List;

public interface VideoViewRepository {

    List<VideoPreview> getVideoPreviews(List<Integer> idList);
}
