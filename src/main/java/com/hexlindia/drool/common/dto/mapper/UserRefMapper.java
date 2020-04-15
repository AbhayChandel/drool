package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface UserRefMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    UserRef toDoc(UserRefDto userRefDto);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    UserRefDto toDto(UserRef userRef);
}
