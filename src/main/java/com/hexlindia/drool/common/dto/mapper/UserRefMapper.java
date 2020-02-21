package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.common.dto.UserRefDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRefMapper {

    UserRef toDoc(UserRefDto userRefDto);

    UserRefDto toDto(UserRef userRef);
}
