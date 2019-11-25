package com.hexlindia.drool.usermanagement.security.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationDetailsMapper {

    UserAuthenticationEntity toEntity(UserRegistrationDetailsTo userRegistrationDetailsTo);
}
