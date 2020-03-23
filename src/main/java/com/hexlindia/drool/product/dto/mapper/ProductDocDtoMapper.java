package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDocDtoMapper {

    @Mapping(target = "aspectResults", source = "aspectsDoc.aspectResultDocList")
    ProductDto toDto(ProductDoc productDoc);
}
