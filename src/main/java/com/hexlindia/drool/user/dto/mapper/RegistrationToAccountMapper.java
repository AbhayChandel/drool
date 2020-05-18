package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationToAccountMapper {

    UserAccountEntity toEntity(UserRegistrationDto userRegistrationDto);
}
