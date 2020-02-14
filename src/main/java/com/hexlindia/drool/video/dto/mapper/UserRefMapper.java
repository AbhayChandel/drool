package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.dto.UserRefDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRefMapper {

    UserRef toDoc(UserRefDto userRefDto);

    UserRefDto toDto(UserRef userRef);
}
