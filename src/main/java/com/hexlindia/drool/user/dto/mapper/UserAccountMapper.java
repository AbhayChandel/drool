package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.dto.UserAccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccountDto toDto(UserAccountDoc userAccountDoc);

    UserAccountDoc toDoc(UserAccountDto userAccountDto);
}
