package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.StatNumberToCompactString;
import com.hexlindia.drool.common.dto.mapper.StatsFieldMapper;
import com.hexlindia.drool.common.dto.mapper.StringToEntityMapping;
import com.hexlindia.drool.post.data.entity.VideoEntity;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.user.dto.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PostTypeMapper.class, UserMapper.class, StatsFieldMapper.class})
public interface PostDtoVideoEntityMapper {

    @Mapping(source = "type", target = "type", qualifiedBy = EnumToEntityMapping.class)
    @Mapping(source = "ownerId", target = "owner", qualifiedBy = StringToEntityMapping.class)
    VideoEntity toEntity(PostDto postDto);

    @Mapping(source = "type", target = "type", qualifiedBy = EntityToEnumMapping.class)
    @Mapping(source = "owner.id", target = "ownerId", qualifiedBy = StringToEntityMapping.class)
    @Mapping(source = "likes", target = "likes", qualifiedBy = StatNumberToCompactString.class)
    @Mapping(source = "views", target = "views", qualifiedBy = StatNumberToCompactString.class)
    PostDto toDto(VideoEntity saveVideoEntity);
}
