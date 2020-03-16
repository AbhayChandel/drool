package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDocDtoMapper {

    ProductDto toDto(ProductDoc productDoc);
}
