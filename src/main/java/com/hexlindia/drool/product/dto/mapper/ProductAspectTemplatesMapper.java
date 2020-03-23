package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMappingAnnotation;
import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AspectTemplateMapper.class})
public interface ProductAspectTemplatesMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMappingAnnotation.class)
    @Mapping(source = "aspectTemplates", target = "aspectTemplateDtoList")
    ProductAspectTemplatesDto toDto(ProductAspectTemplates productAspectTemplates);

}
