package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.dto.UserAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(source = "email", target = "emailId")
    UserAccountDto toDto(UserAccountEntity userAccountEntity);

    @Mapping(source = "emailId", target = "email")
    UserAccountEntity toEntity(UserAccountDto userAccountDto);
}
