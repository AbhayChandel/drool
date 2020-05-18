package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserAccountVerificationEntity;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserVerificationMapper {

    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "verificationType", source = "id.verificationType.name")
    @Mapping(target = "verified", source = "verified")
    UserVerificationDto toDto(UserAccountVerificationEntity userAccountVerificationEntity);

    List<UserVerificationDto> toDtoList(List<UserAccountVerificationEntity> userAccountVerificationEntityList);
}
