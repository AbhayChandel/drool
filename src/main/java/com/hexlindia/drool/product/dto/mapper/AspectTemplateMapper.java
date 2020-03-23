package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMappingAnnotation;
import com.hexlindia.drool.product.data.doc.AspectTemplate;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class AspectTemplateMapper {


    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMappingAnnotation.class)
    abstract AspectTemplateDto templateDto(AspectTemplate aspectTemplate);

    abstract List<AspectTemplateDto> toDtoList(List<AspectTemplate> aspectTemplateList);

    @ObjectIdToStringMappingAnnotation
    static String ObjectIdToString(ObjectId id) {
        if (id == null) {
            log.error("Id for AspectTemplate is NULL");
        }
        return id.toHexString();
    }
}
