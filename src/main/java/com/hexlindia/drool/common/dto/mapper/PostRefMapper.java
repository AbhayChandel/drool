package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface PostRefMapper {


    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    PostRef toDoc(PostRefDto postRefDto);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    PostRefDto toDto(PostRef postRef);
}
