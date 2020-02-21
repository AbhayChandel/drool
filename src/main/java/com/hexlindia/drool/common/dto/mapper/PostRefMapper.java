package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostRefMapper {

    PostRef toDoc(PostRefDto postRefDto);

    PostRefDto toDto(PostRef postRef);
}
