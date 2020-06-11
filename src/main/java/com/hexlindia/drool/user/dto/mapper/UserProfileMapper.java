package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.dto.UserProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    // TO Do: check why this not accessible to unit test with default scope
    UserProfileEntity toEntity(UserProfileDto userProfileDto);


    UserProfileDto toDto(UserProfileEntity userProfileEntity);
}
