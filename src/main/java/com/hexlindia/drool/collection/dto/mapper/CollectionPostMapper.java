package com.hexlindia.drool.collection.dto.mapper;

import com.hexlindia.drool.collection.data.entity.CollectionEntity;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.common.data.entity.VisibilityEntity;
import com.hexlindia.drool.common.data.repository.VisibilityRepository;
import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.common.error.exception.VisibilityNotFoundException;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class CollectionPostMapper {

    @Autowired
    private VisibilityRepository visibilityRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    /*@Mapping(source = "id", target = "collectionId")
    @Mapping(source = "visibility", target = "visibility", ignore = true)
    @Mapping(source = "owner.id", target = "ownerId")
    public abstract CollectionPostDto toDto(CollectionEntity collectionEntity);

    @AfterMapping
    void afterToDto(CollectionEntity collectionEntity, @MappingTarget CollectionPostDto collectionPostDto) {
        setVisibilityInDto(collectionEntity.getVisibility().getVisibility(), collectionPostDto);
    }


    void setVisibilityInDto(String visibility, CollectionPostDto collectionPostDto) {
        if(visibility == null){
            collectionPostDto.setVisibility(Visibility.PUBLIC);
            return;
        }
        switch (visibility) {
            case "private":
                collectionPostDto.setVisibility(Visibility.PRIVATE);
                return;
            default:
                collectionPostDto.setVisibility(Visibility.PUBLIC);
        }
    }*/

    @Mapping(source = "visibility", target = "visibility", ignore = true)
    @Mapping(source = "collectionId", target = "id")
    public abstract CollectionEntity toEntity(CollectionPostDto collectionPostDto);

    @AfterMapping
    void afterToEntity(CollectionPostDto collectionPostDto, @MappingTarget CollectionEntity collectionEntity) {
        setVisibilityInEntity(collectionPostDto, collectionEntity);
        setOwner(collectionPostDto, collectionEntity);
    }

    void setVisibilityInEntity(CollectionPostDto collectionPostDto, @MappingTarget CollectionEntity collectionEntity) {
        if (collectionPostDto.getVisibility() != null) {
            Optional<VisibilityEntity> visibility = visibilityRepository.findByVisibility(collectionPostDto.getVisibility().toString().toLowerCase());
            if (visibility.isPresent()) {
                collectionEntity.setVisibility(visibility.get());
                return;
            }
            log.error("Visibility '" + collectionPostDto.getVisibility().toString() + "' not found");
            throw new VisibilityNotFoundException("Visibility '" + collectionPostDto.getVisibility().toString() + "' not found");
        }
    }

    void setOwner(CollectionPostDto collectionPostDto, @MappingTarget CollectionEntity collectionEntity) {
        if (collectionPostDto.getOwnerId() != null) {
            Optional<UserAccountEntity> userAccount = userAccountRepository.findById(Long.valueOf(collectionPostDto.getOwnerId()));
            if (userAccount.isPresent()) {
                collectionEntity.setOwner(userAccount.get());
                return;
            }
            log.error("User Account with id '" + collectionPostDto.getOwnerId() + "' not found. Location: " + this.getClass().getSimpleName());
            throw new UserAccountNotFoundException("User Account with id '" + collectionPostDto.getOwnerId() + "' not found");
        } else if (collectionPostDto.getCollectionId() == null) {
            log.error("While mapping " + collectionPostDto.getClass().getSimpleName() + " to " + collectionEntity.getClass().getSimpleName() +
                    " collectionId and ownerId both are empty");
            throw new RequestParameterNotValidException("OwnerId", "OwnerId cannot be NULL, If collectionId is null");
        }
    }

}


