package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.post.business.exception.PostTypeNotFoundException;
import com.hexlindia.drool.post.data.entity.PostTypeEntity;
import com.hexlindia.drool.post.data.repository.api.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostTypeMapper {

    private final PostTypeRepository postTypeRepository;

    @EnumToEntityMapping
    public PostTypeEntity enumToEntity(PostType2 type) {
        if (type != null) {
            return getPostTypeEntity(type);
        } else {
            log.error("While mapping PostDto to entity Post type is empty");
            throw new RequestParameterNotValidException("type", "type cannot be NULL");
        }
    }

    PostTypeEntity getPostTypeEntity(PostType2 type) {
        Optional<PostTypeEntity> postTypeEntityOptional = postTypeRepository.findByType(type.toString().toLowerCase());
        if (postTypeEntityOptional.isPresent()) {
            return postTypeEntityOptional.get();
        }
        log.error("Post type '" + type.toString() + "' not found in data store");
        throw new PostTypeNotFoundException("Post type '" + type.toString() + "' not found in data store");
    }

    @EntityToEnumMapping
    public PostType2 entityToEnum(PostTypeEntity postTypeEntity) {
        if (postTypeEntity == null) {
            return null;
        }
        return getPostTypeEnum(postTypeEntity.getId());
    }

    PostType2 getPostTypeEnum(int id) {
        switch (id) {
            case 1:
                return PostType2.VIDEO;
            case 2:
                return PostType2.ARTICLE;
            case 3:
                return PostType2.DISCUSSION;
            default:
                return null;
        }
    }
}
