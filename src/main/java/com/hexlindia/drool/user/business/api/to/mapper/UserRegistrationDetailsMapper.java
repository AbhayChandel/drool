package com.hexlindia.drool.user.business.api.to.mapper;

import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationDetailsMapper {

    UserAccountEntity toEntity(UserRegistrationDetailsTo userRegistrationDetailsTo);
}
