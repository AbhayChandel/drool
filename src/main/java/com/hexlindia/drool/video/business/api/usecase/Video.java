package com.hexlindia.drool.video.business.api.usecase;

import com.hexlindia.drool.video.dto.VideoDto;

public interface Video {

    public VideoDto insert(VideoDto videoDto);

    public VideoDto findById(String id);
}
