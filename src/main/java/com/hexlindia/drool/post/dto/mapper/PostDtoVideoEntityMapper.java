package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.video.data.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostDtoVideoEntityMapper {

    @Mapping(source = "owner", target = "owner", ignore = true)
    @Mapping(source = "postType", target = "postType", ignore = true)
    VideoEntity toEntity(PostDto postDto);

    @Mapping(source = "owner", target = "owner", ignore = true)
    @Mapping(source = "postType", target = "postType", ignore = true)
    PostDto toDto(VideoEntity saveVideoEntity);
}
