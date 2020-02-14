package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoDocDtoMapper {

    VideoDoc toDoc(VideoDto videoDto);

    VideoDto toDto(VideoDoc videoDoc);
}
