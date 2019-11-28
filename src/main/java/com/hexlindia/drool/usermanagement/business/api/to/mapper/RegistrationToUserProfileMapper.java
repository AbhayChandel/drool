package com.hexlindia.drool.usermanagement.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationToUserProfileMapper {

    UserProfileTo toUserProfileTo(UserRegistrationDetailsTo userRegistrationDetailsTo);
}
