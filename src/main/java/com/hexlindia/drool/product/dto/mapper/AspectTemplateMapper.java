package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.product.data.doc.AspectTemplate;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface AspectTemplateMapper {


    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    AspectTemplateDto templateDto(AspectTemplate aspectTemplate);

    List<AspectTemplateDto> toDtoList(List<AspectTemplate> aspectTemplateList);
}
