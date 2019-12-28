package com.hexlindia.drool.user.business.api.to.mapper;

import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationToUserProfileMapper {

    UserProfileTo toUserProfileTo(UserRegistrationDetailsTo userRegistrationDetailsTo);
}
