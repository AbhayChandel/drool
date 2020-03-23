package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMappingAnnotation;
import com.hexlindia.drool.product.data.doc.AspectTemplate;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AspectTemplateMapper {


    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMappingAnnotation.class)
    AspectTemplateDto templateDto(AspectTemplate aspectTemplate);

    List<AspectTemplateDto> toDtoList(List<AspectTemplate> aspectTemplateList);

    @ObjectIdToStringMappingAnnotation
    static String ObjectIdToString(ObjectId id) {
        return id == null ? "" : id.toHexString();
    }
}
