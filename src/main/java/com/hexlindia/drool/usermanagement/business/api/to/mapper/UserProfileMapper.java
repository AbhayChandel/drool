package com.hexlindia.drool.usermanagement.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.data.entity.UserProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    // TO Do: check why this not accessible to unit test with default scope
    UserProfileEntity toEntity(UserProfileTo userProfileTo);


    UserProfileTo toTransferObject(UserProfileEntity userProfileEntity);
}
