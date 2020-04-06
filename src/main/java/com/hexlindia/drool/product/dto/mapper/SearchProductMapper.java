package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.product.data.doc.SearchProductRef;
import com.hexlindia.drool.product.dto.SearchProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BrandRefMapper.class, ObjectIdMapper.class})
public interface SearchProductMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(source = "brandRef", target = "brandRefDto")
    SearchProductDto toDto(SearchProductRef searchProductRef);

    List<SearchProductDto> toDtoList(List<SearchProductRef> searchProductRefList);
}
