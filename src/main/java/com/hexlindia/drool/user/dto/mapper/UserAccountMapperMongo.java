package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.dto.UserAccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapperMongo {

    UserAccountDto toTransferObject(UserAccountEntity userAccountEntity);
}
