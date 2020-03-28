package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, AspectTemplateMapper.class})
public interface ProductAspectTemplatesMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(source = "aspectTemplates", target = "aspectTemplateDtoList")
    ProductAspectTemplatesDto toDto(ProductAspectTemplates productAspectTemplates);
}
