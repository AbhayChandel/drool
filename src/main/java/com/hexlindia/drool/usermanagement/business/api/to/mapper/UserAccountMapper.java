package com.hexlindia.drool.usermanagement.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccountTo toTransferObject(UserAccountEntity userAccountEntity);
}
