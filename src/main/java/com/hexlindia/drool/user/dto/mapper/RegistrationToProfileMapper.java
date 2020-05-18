package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationToProfileMapper {

    UserProfileDto toUserProfileTo(UserRegistrationDto userRegistrationDto);
}
