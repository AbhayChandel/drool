package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostDtoArticleEntityMapper {

    @Mapping(source = "owner", target = "owner", ignore = true)
    @Mapping(source = "postType", target = "postType", ignore = true)
    ArticleEntity toEntity(PostDto postDto);

    @Mapping(source = "owner", target = "owner", ignore = true)
    @Mapping(source = "postType", target = "postType", ignore = true)
    PostDto toDto(ArticleEntity articleEntity);
}
